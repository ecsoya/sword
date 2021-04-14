package com.soyatec.sword.framework.web.domain;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.soyatec.sword.common.utils.Arith;
import com.soyatec.sword.common.utils.IpUtils;
import com.soyatec.sword.framework.web.domain.server.Cpu;
import com.soyatec.sword.framework.web.domain.server.Jvm;
import com.soyatec.sword.framework.web.domain.server.Mem;
import com.soyatec.sword.framework.web.domain.server.Sys;
import com.soyatec.sword.framework.web.domain.server.SysFile;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 服务器相关信息
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class Server {

	private static final int OSHI_WAIT_SECOND = 1000;

	/**
	 * CPU相关信息
	 */
	private Cpu cpu = new Cpu();

	/**
	 * 內存相关信息
	 */
	private Mem mem = new Mem();

	/**
	 * JVM相关信息
	 */
	private Jvm jvm = new Jvm();

	/**
	 * 服务器相关信息
	 */
	private Sys sys = new Sys();

	/**
	 * 磁盘相关信息
	 */
	private List<SysFile> sysFiles = new LinkedList<SysFile>();

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public Mem getMem() {
		return mem;
	}

	public void setMem(Mem mem) {
		this.mem = mem;
	}

	public Jvm getJvm() {
		return jvm;
	}

	public void setJvm(Jvm jvm) {
		this.jvm = jvm;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public List<SysFile> getSysFiles() {
		return sysFiles;
	}

	public void setSysFiles(List<SysFile> sysFiles) {
		this.sysFiles = sysFiles;
	}

	public void copyTo() throws Exception {
		final SystemInfo si = new SystemInfo();
		final HardwareAbstractionLayer hal = si.getHardware();

		setCpuInfo(hal.getProcessor());

		setMemInfo(hal.getMemory());

		setSysInfo();

		setJvmInfo();

		setSysFiles(si.getOperatingSystem());
	}

	/**
	 * 设置CPU信息
	 */
	private void setCpuInfo(CentralProcessor processor) {
		// CPU信息
		final long[] prevTicks = processor.getSystemCpuLoadTicks();
		Util.sleep(OSHI_WAIT_SECOND);
		final long[] ticks = processor.getSystemCpuLoadTicks();
		final long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		final long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		final long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		final long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		final long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		final long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		final long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		final long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		final long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
		cpu.setCpuNum(processor.getLogicalProcessorCount());
		cpu.setTotal(totalCpu);
		cpu.setSys(cSys);
		cpu.setUsed(user);
		cpu.setWait(iowait);
		cpu.setFree(idle);
	}

	/**
	 * 设置内存信息
	 */
	private void setMemInfo(GlobalMemory memory) {
		mem.setTotal(memory.getTotal());
		mem.setUsed(memory.getTotal() - memory.getAvailable());
		mem.setFree(memory.getAvailable());
	}

	/**
	 * 设置服务器信息
	 */
	private void setSysInfo() {
		final Properties props = System.getProperties();
		sys.setComputerName(IpUtils.getHostName());
		sys.setComputerIp(IpUtils.getHostIp());
		sys.setOsName(props.getProperty("os.name"));
		sys.setOsArch(props.getProperty("os.arch"));
		sys.setUserDir(props.getProperty("user.dir"));
	}

	/**
	 * 设置Java虚拟机
	 */
	private void setJvmInfo() throws UnknownHostException {
		final Properties props = System.getProperties();
		jvm.setTotal(Runtime.getRuntime().totalMemory());
		jvm.setMax(Runtime.getRuntime().maxMemory());
		jvm.setFree(Runtime.getRuntime().freeMemory());
		jvm.setVersion(props.getProperty("java.version"));
		jvm.setHome(props.getProperty("java.home"));
	}

	/**
	 * 设置磁盘信息
	 */
	private void setSysFiles(OperatingSystem os) {
		final FileSystem fileSystem = os.getFileSystem();
		final List<OSFileStore> fsArray = fileSystem.getFileStores();
		for (final OSFileStore fs : fsArray) {
			final long free = fs.getUsableSpace();
			final long total = fs.getTotalSpace();
			final long used = total - free;
			final SysFile sysFile = new SysFile();
			sysFile.setDirName(fs.getMount());
			sysFile.setSysTypeName(fs.getType());
			sysFile.setTypeName(fs.getName());
			sysFile.setTotal(convertFileSize(total));
			sysFile.setFree(convertFileSize(free));
			sysFile.setUsed(convertFileSize(used));
			sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
			sysFiles.add(sysFile);
		}
	}

	/**
	 * 字节转换
	 *
	 * @param size 字节大小
	 * @return 转换后值
	 */
	public String convertFileSize(long size) {
		final long kb = 1024;
		final long mb = kb * 1024;
		final long gb = mb * 1024;
		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			final float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			final float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else {
			return String.format("%d B", size);
		}
	}
}
