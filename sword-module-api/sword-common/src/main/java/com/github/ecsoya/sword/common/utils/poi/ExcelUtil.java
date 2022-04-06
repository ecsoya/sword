package com.github.ecsoya.sword.common.utils.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.annotation.Excel.Type;
import com.github.ecsoya.sword.common.annotation.Excels;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.DictUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.file.FileTypeUtils;
import com.github.ecsoya.sword.common.utils.file.ImageUtils;
import com.github.ecsoya.sword.common.utils.reflect.ReflectUtils;

/**
 * The Class ExcelUtil.
 *
 * @param <T> the generic type
 */
public class ExcelUtil<T> {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/** The Constant sheetSize. */
	public static final int sheetSize = 65536;

	/** The sheet name. */
	private String sheetName;

	/** The type. */
	private Type type;

	/** The wb. */
	private Workbook wb;

	/** The sheet. */
	private Sheet sheet;

	/** The styles. */
	private Map<String, CellStyle> styles;

	/** The list. */
	private List<T> list;

	/** The fields. */
	private List<Object[]> fields;

	/** The max height. */
	private short maxHeight;

	/** The statistics. */
	private final Map<Integer, Double> statistics = new HashMap<Integer, Double>();

	/** The Constant DOUBLE_FORMAT. */
	private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

	/** The clazz. */
	public Class<T> clazz;

	/**
	 * Instantiates a new excel util.
	 *
	 * @param clazz 实体对象.
	 */
	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Inits the.
	 *
	 * @param list      the list
	 * @param sheetName the sheet name
	 * @param type      the type
	 */
	public void init(List<T> list, String sheetName, Type type) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		this.list = list;
		this.sheetName = sheetName;
		this.type = type;
		createExcelField();
		createWorkbook();
	}

	/**
	 * Import excel.
	 *
	 * @param is the is
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<T> importExcel(InputStream is) throws Exception {
		return importExcel(org.apache.commons.lang3.StringUtils.EMPTY, is);
	}

	/**
	 * Import excel.
	 *
	 * @param sheetName the sheet name
	 * @param is        the is
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<T> importExcel(String sheetName, InputStream is) throws Exception {
		this.type = Type.IMPORT;
		this.wb = WorkbookFactory.create(is);
		final List<T> list = new ArrayList<T>();
		Sheet sheet = null;
		if (StringUtils.isNotEmpty(sheetName)) {
			// 如果指定sheet名,则取指定sheet中的内容.
			sheet = wb.getSheet(sheetName);
		} else {
			// 如果传入的sheet名不存在则默认指向第1个sheet.
			sheet = wb.getSheetAt(0);
		}

		if (sheet == null) {
			throw new IOException("文件sheet不存在");
		}

		final int rows = sheet.getPhysicalNumberOfRows();

		if (rows > 0) {
			// 定义一个map用于存放excel列的序号和field.
			final Map<String, Integer> cellMap = new HashMap<String, Integer>();
			// 获取表头
			final Row heard = sheet.getRow(0);
			for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
				final Cell cell = heard.getCell(i);
				if (StringUtils.isNotNull(cell)) {
					final String value = this.getCellValue(heard, i).toString();
					cellMap.put(value, i);
				} else {
					cellMap.put(null, i);
				}
			}
			// 有数据时才处理 得到类的所有field.
			final Field[] allFields = clazz.getDeclaredFields();
			// 定义一个map用于存放列的序号和field.
			final Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
			for (int col = 0; col < allFields.length; col++) {
				final Field field = allFields[col];
				final Excel attr = field.getAnnotation(Excel.class);
				if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
					// 设置类的私有字段属性可访问.
					field.setAccessible(true);
					final Integer column = cellMap.get(attr.name());
					if (column != null) {
						fieldsMap.put(column, field);
					}
				}
			}
			for (int i = 1; i < rows; i++) {
				// 从第2行开始取数据,默认第一行是表头.
				final Row row = sheet.getRow(i);
				T entity = null;
				for (final Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
					Object val = this.getCellValue(row, entry.getKey());

					// 如果不存在实例则新建.
					entity = (entity == null ? clazz.newInstance() : entity);
					// 从map中得到对应列的field.
					final Field field = fieldsMap.get(entry.getKey());
					// 取得类型,并根据对象类型设置值.
					final Class<?> fieldType = field.getType();
					if (String.class == fieldType) {
						final String s = Convert.toStr(val);
						if (org.apache.commons.lang3.StringUtils.endsWith(s, ".0")) {
							val = org.apache.commons.lang3.StringUtils.substringBefore(s, ".0");
						} else {
							final String dateFormat = field.getAnnotation(Excel.class).dateFormat();
							if (StringUtils.isNotEmpty(dateFormat)) {
								val = DateUtils.parseDateToStr(dateFormat, (Date) val);
							} else {
								val = Convert.toStr(val);
							}
						}
					} else if ((Integer.TYPE == fieldType || Integer.class == fieldType)
							&& org.apache.commons.lang3.StringUtils.isNumeric(Convert.toStr(val))) {
						val = Convert.toInt(val);
					} else if (Long.TYPE == fieldType || Long.class == fieldType) {
						val = Convert.toLong(val);
					} else if (Double.TYPE == fieldType || Double.class == fieldType) {
						val = Convert.toDouble(val);
					} else if (Float.TYPE == fieldType || Float.class == fieldType) {
						val = Convert.toFloat(val);
					} else if (BigDecimal.class == fieldType) {
						val = Convert.toBigDecimal(val);
					} else if (Date.class == fieldType) {
						if (val instanceof String) {
							val = DateUtils.parseDate(val);
						} else if (val instanceof Double) {
							val = DateUtil.getJavaDate((Double) val);
						}
					} else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
						val = Convert.toBool(val, false);
					}
					if (StringUtils.isNotNull(fieldType)) {
						final Excel attr = field.getAnnotation(Excel.class);
						String propertyName = field.getName();
						if (StringUtils.isNotEmpty(attr.targetAttr())) {
							propertyName = field.getName() + "." + attr.targetAttr();
						} else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
							val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
						} else if (StringUtils.isNotEmpty(attr.dictType())) {
							val = reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
						}
						ReflectUtils.invokeSetter(entity, propertyName, val);
					}
				}
				list.add(entity);
			}
		}
		return list;
	}

	/**
	 * Export excel.
	 *
	 * @param list      the list
	 * @param sheetName the sheet name
	 * @return the ajax result
	 */
	public AjaxResult exportExcel(List<T> list, String sheetName) {
		this.init(list, sheetName, Type.EXPORT);
		return exportExcel(Collections.emptyList());
	}

	/**
	 * Import template excel.
	 *
	 * @param sheetName   the sheet name
	 * @param filterNames the filter names
	 * @return the ajax result
	 */
	public AjaxResult importTemplateExcel(String sheetName, String... filterNames) {
		this.init(null, sheetName, Type.IMPORT);
		List<String> filters = null;
		if (filterNames != null && filterNames.length > 0) {
			filters = Arrays.asList(filterNames);
		}
		return exportExcel(filters);
	}

	/**
	 * Export excel.
	 *
	 * @param filterNames the filter names
	 * @return the ajax result
	 */
	public AjaxResult exportExcel(List<String> filterNames) {
		OutputStream out = null;
		try {
			// 取出一共有多少个sheet.
			final double sheetNo = Math.ceil(list.size() / sheetSize);
			for (int index = 0; index <= sheetNo; index++) {
				createSheet(sheetNo, index);

				// 产生一行
				final Row row = sheet.createRow(0);
				int column = 0;
				// 写入各个字段的列头名称
				for (final Object[] os : fields) {
					final Excel excel = (Excel) os[1];
					if (filterNames != null && !filterNames.contains(excel.name())) {
						continue;
					}
					this.createCell(excel, row, column++);
				}
				if (Type.EXPORT.equals(type)) {
					fillExcelData(index, row);
				}
			}
			final String filename = encodingFilename(sheetName);
			out = new FileOutputStream(getAbsoluteFile(filename));
			wb.write(out);
			return AjaxResult.success(filename);
		} catch (final Exception e) {
			log.error("导出Excel异常{}", e.getMessage());
			throw new BusinessException("导出Excel失败，请联系网站管理员！");
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Fill excel data.
	 *
	 * @param index the index
	 * @param row   the row
	 */
	public void fillExcelData(int index, Row row) {
		final int startNo = index * sheetSize;
		final int endNo = Math.min(startNo + sheetSize, list.size());
		for (int i = startNo; i < endNo; i++) {
			row = sheet.createRow(i + 1 - startNo);
			// 得到导出对象.
			final T vo = list.get(i);
			int column = 0;
			for (final Object[] os : fields) {
				final Field field = (Field) os[0];
				final Excel excel = (Excel) os[1];
				// 设置实体类私有属性可访问
				field.setAccessible(true);
				this.addCell(excel, row, vo, field, column++);
			}
		}
	}

	/**
	 * Creates the styles.
	 *
	 * @param wb the wb
	 * @return the map
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		// 写入各条记录,每条记录对应excel表中的一行
		final Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		final Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		final Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		final Font totalFont = wb.createFont();
		totalFont.setFontName("Arial");
		totalFont.setFontHeightInPoints((short) 10);
		style.setFont(totalFont);
		styles.put("total", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("data3", style);

		return styles;
	}

	/**
	 * Creates the cell.
	 *
	 * @param attr   the attr
	 * @param row    the row
	 * @param column the column
	 * @return the cell
	 */
	public Cell createCell(Excel attr, Row row, int column) {
		// 创建列
		final Cell cell = row.createCell(column);
		// 写入列信息
		cell.setCellValue(attr.name());
		setDataValidation(attr, row, column);
		cell.setCellStyle(styles.get("header"));
		return cell;
	}

	/**
	 * Sets the cell vo.
	 *
	 * @param value the value
	 * @param attr  the attr
	 * @param cell  the cell
	 */
	public void setCellVo(Object value, Excel attr, Cell cell) {
		if (ColumnType.STRING == attr.cellType()) {
			cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
		} else if (ColumnType.NUMERIC == attr.cellType()) {
			cell.setCellValue(
					org.apache.commons.lang3.StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value)
							: Convert.toInt(value));
		} else if (ColumnType.IMAGE == attr.cellType()) {
			final ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
					cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
			final String imagePath = Convert.toStr(value);
			if (StringUtils.isNotEmpty(imagePath)) {
				final byte[] data = ImageUtils.getImage(imagePath);
				getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
						cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
			}
		}
	}

	/**
	 * Gets the drawing patriarch.
	 *
	 * @param sheet the sheet
	 * @return the drawing patriarch
	 */
	public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
		if (sheet.getDrawingPatriarch() == null) {
			sheet.createDrawingPatriarch();
		}
		return sheet.getDrawingPatriarch();
	}

	/**
	 * Gets the image type.
	 *
	 * @param value the value
	 * @return the image type
	 */
	public int getImageType(byte[] value) {
		final String type = FileTypeUtils.getFileExtendName(value);
		if ("JPG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_JPEG;
		} else if ("PNG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_PNG;
		}
		return Workbook.PICTURE_TYPE_JPEG;
	}

	/**
	 * Sets the data validation.
	 *
	 * @param attr   the attr
	 * @param row    the row
	 * @param column the column
	 */
	public void setDataValidation(Excel attr, Row row, int column) {
		if (attr.name().indexOf("注：") >= 0) {
			sheet.setColumnWidth(column, 6000);
		} else {
			// 设置列宽
			sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
		}
		// 如果设置了提示信息则鼠标放上去提示.
		if (StringUtils.isNotEmpty(attr.prompt())) {
			// 这里默认设了2-101列提示.
			setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
		}
		// 如果设置了combo属性则本列只能选择不能输入
		if (attr.combo().length > 0) {
			// 这里默认设了2-101列只能选择不能输入.
			setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
		}
	}

	/**
	 * Adds the cell.
	 *
	 * @param attr   the attr
	 * @param row    the row
	 * @param vo     the vo
	 * @param field  the field
	 * @param column the column
	 * @return the cell
	 */
	public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
		Cell cell = null;
		try {
			// 设置行高
			row.setHeight(maxHeight);
			// 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
			if (attr.isExport()) {
				// 创建cell
				cell = row.createCell(column);
				final int align = attr.align().value();
				cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));

				// 用于读取对象中的属性
				final Object value = getTargetValue(vo, field, attr);
				final String dateFormat = attr.dateFormat();
				final String readConverterExp = attr.readConverterExp();
				final String separator = attr.separator();
				final String dictType = attr.dictType();
				if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
					cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
				} else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
					cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
				} else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
					cell.setCellValue(convertDictByExp(Convert.toStr(value), dictType, separator));
				} else if (value instanceof BigDecimal && -1 != attr.scale()) {
					cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
				} else {
					// 设置列类型
					setCellVo(value, attr, cell);
				}
				addStatisticsData(column, Convert.toStr(value), attr);
			}
		} catch (final Exception e) {
			log.error("导出Excel失败{}", e);
		}
		return cell;
	}

	/**
	 * Sets the XSSF prompt.
	 *
	 * @param sheet         the sheet
	 * @param promptTitle   the prompt title
	 * @param promptContent the prompt content
	 * @param firstRow      the first row
	 * @param endRow        the end row
	 * @param firstCol      the first col
	 * @param endCol        the end col
	 */
	public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
			int firstCol, int endCol) {
		final DataValidationHelper helper = sheet.getDataValidationHelper();
		final DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
		final CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		final DataValidation dataValidation = helper.createValidation(constraint, regions);
		dataValidation.createPromptBox(promptTitle, promptContent);
		dataValidation.setShowPromptBox(true);
		sheet.addValidationData(dataValidation);
	}

	/**
	 * Sets the XSSF validation.
	 *
	 * @param sheet    the sheet
	 * @param textlist the textlist
	 * @param firstRow the first row
	 * @param endRow   the end row
	 * @param firstCol the first col
	 * @param endCol   the end col
	 */
	public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
		final DataValidationHelper helper = sheet.getDataValidationHelper();
		// 加载下拉列表内容
		final DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		final CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		final DataValidation dataValidation = helper.createValidation(constraint, regions);
		// 处理Excel兼容性问题
		if (dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);
		} else {
			dataValidation.setSuppressDropDownArrow(false);
		}

		sheet.addValidationData(dataValidation);
	}

	/**
	 * Convert by exp.
	 *
	 * @param propertyValue the property value
	 * @param converterExp  the converter exp
	 * @param separator     the separator
	 * @return the string
	 */
	public static String convertByExp(String propertyValue, String converterExp, String separator) {
		final StringBuilder propertyString = new StringBuilder();
		final String[] convertSource = converterExp.split(",");
		for (final String item : convertSource) {
			final String[] itemArray = item.split("=");
			if (org.apache.commons.lang3.StringUtils.containsAny(separator, propertyValue)) {
				for (final String value : propertyValue.split(separator)) {
					if (itemArray[0].equals(value)) {
						propertyString.append(itemArray[1] + separator);
						break;
					}
				}
			} else if (itemArray[0].equals(propertyValue)) {
				return itemArray[1];
			}
		}
		return org.apache.commons.lang3.StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Reverse by exp.
	 *
	 * @param propertyValue the property value
	 * @param converterExp  the converter exp
	 * @param separator     the separator
	 * @return the string
	 */
	public static String reverseByExp(String propertyValue, String converterExp, String separator) {
		final StringBuilder propertyString = new StringBuilder();
		final String[] convertSource = converterExp.split(",");
		for (final String item : convertSource) {
			final String[] itemArray = item.split("=");
			if (org.apache.commons.lang3.StringUtils.containsAny(separator, propertyValue)) {
				for (final String value : propertyValue.split(separator)) {
					if (itemArray[1].equals(value)) {
						propertyString.append(itemArray[0] + separator);
						break;
					}
				}
			} else if (itemArray[1].equals(propertyValue)) {
				return itemArray[0];
			}
		}
		return org.apache.commons.lang3.StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Convert dict by exp.
	 *
	 * @param dictValue the dict value
	 * @param dictType  the dict type
	 * @param separator the separator
	 * @return the string
	 */
	public static String convertDictByExp(String dictValue, String dictType, String separator) {
		return DictUtils.getDictLabel(dictType, dictValue, separator);
	}

	/**
	 * Reverse dict by exp.
	 *
	 * @param dictLabel the dict label
	 * @param dictType  the dict type
	 * @param separator the separator
	 * @return the string
	 */
	public static String reverseDictByExp(String dictLabel, String dictType, String separator) {
		return DictUtils.getDictValue(dictType, dictLabel, separator);
	}

	/**
	 * Adds the statistics data.
	 *
	 * @param index  the index
	 * @param text   the text
	 * @param entity the entity
	 */
	private void addStatisticsData(Integer index, String text, Excel entity) {
		if (entity != null && entity.isStatistics()) {
			Double temp = 0D;
			if (!statistics.containsKey(index)) {
				statistics.put(index, temp);
			}
			try {
				temp = Double.valueOf(text);
			} catch (final NumberFormatException e) {
			}
			statistics.put(index, statistics.get(index) + temp);
		}
	}

	/**
	 * Adds the statistics row.
	 */
	public void addStatisticsRow() {
		if (statistics.size() > 0) {
			Cell cell = null;
			final Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			final Set<Integer> keys = statistics.keySet();
			cell = row.createCell(0);
			cell.setCellStyle(styles.get("total"));
			cell.setCellValue("合计");

			for (final Integer key : keys) {
				cell = row.createCell(key);
				cell.setCellStyle(styles.get("total"));
				cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
			}
			statistics.clear();
		}
	}

	/**
	 * Encoding filename.
	 *
	 * @param filename the filename
	 * @return the string
	 */
	public String encodingFilename(String filename) {
		filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
		return filename;
	}

	/**
	 * Gets the absolute file.
	 *
	 * @param filename the filename
	 * @return the absolute file
	 */
	public String getAbsoluteFile(String filename) {
		final String downloadPath = GlobalConfig.getDownloadPath() + filename;
		final File desc = new File(downloadPath);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}

	/**
	 * Gets the target value.
	 *
	 * @param vo    the vo
	 * @param field the field
	 * @param excel the excel
	 * @return the target value
	 * @throws Exception the exception
	 */
	private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
		Object o = field.get(vo);
		if (StringUtils.isNotEmpty(excel.targetAttr())) {
			final String target = excel.targetAttr();
			if (target.indexOf(".") > -1) {
				final String[] targets = target.split("[.]");
				for (final String name : targets) {
					o = getValue(o, name);
				}
			} else {
				o = getValue(o, target);
			}
		}
		return o;
	}

	/**
	 * Gets the value.
	 *
	 * @param o    the o
	 * @param name the name
	 * @return the value
	 * @throws Exception the exception
	 */
	private Object getValue(Object o, String name) throws Exception {
		if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
			final Class<?> clazz = o.getClass();
			final Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			o = field.get(o);
		}
		return o;
	}

	/**
	 * Creates the excel field.
	 */
	private void createExcelField() {
		this.fields = new ArrayList<Object[]>();
		final List<Field> tempFields = new ArrayList<>();
		tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
		tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		for (final Field field : tempFields) {
			// 单注解
			if (field.isAnnotationPresent(Excel.class)) {
				putToField(field, field.getAnnotation(Excel.class));
			}

			// 多注解
			if (field.isAnnotationPresent(Excels.class)) {
				final Excels attrs = field.getAnnotation(Excels.class);
				final Excel[] excels = attrs.value();
				for (final Excel excel : excels) {
					putToField(field, excel);
				}
			}
		}
		this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort()))
				.collect(Collectors.toList());
		this.maxHeight = getRowHeight();
	}

	/**
	 * Gets the row height.
	 *
	 * @return the row height
	 */
	public short getRowHeight() {
		double maxHeight = 0;
		for (final Object[] os : this.fields) {
			final Excel excel = (Excel) os[1];
			maxHeight = maxHeight > excel.height() ? maxHeight : excel.height();
		}
		return (short) (maxHeight * 20);
	}

	/**
	 * Put to field.
	 *
	 * @param field the field
	 * @param attr  the attr
	 */
	private void putToField(Field field, Excel attr) {
		if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
			this.fields.add(new Object[] { field, attr });
		}
	}

	/**
	 * Creates the workbook.
	 */
	public void createWorkbook() {
		this.wb = new SXSSFWorkbook(500);
	}

	/**
	 * Creates the sheet.
	 *
	 * @param sheetNo the sheet no
	 * @param index   the index
	 */
	public void createSheet(double sheetNo, int index) {
		this.sheet = wb.createSheet();
		this.styles = createStyles(wb);
		// 设置工作表的名称.
		if (sheetNo == 0) {
			wb.setSheetName(index, sheetName);
		} else {
			wb.setSheetName(index, sheetName + index);
		}
	}

	/**
	 * Gets the cell value.
	 *
	 * @param row    the row
	 * @param column the column
	 * @return the cell value
	 */
	public Object getCellValue(Row row, int column) {
		if (row == null) {
			return row;
		}
		Object val = "";
		try {
			final Cell cell = row.getCell(column);
			if (StringUtils.isNotNull(cell)) {
				if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
					val = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {
						val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
					} else if ((Double) val % 1 > 0) {
						val = new BigDecimal(val.toString());
					} else {
						val = new DecimalFormat("0").format(val);
					}
				} else if (cell.getCellType() == CellType.STRING) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == CellType.BOOLEAN) {
					val = cell.getBooleanCellValue();
				} else if (cell.getCellType() == CellType.ERROR) {
					val = cell.getErrorCellValue();
				}

			}
		} catch (final Exception e) {
			return val;
		}
		return val;
	}
}