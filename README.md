### Sword框架说明

1. sword-module-api 通用工具集

	* sword-admin 后台模块，集成了后台的主要功能
	* sword-app 前端API模块，集成了通用API模块
	* sword-code 发送验证码通用模块
	* sword-code-m5c 集成了美联短信验证码模块
	* sword-code-sendcloud 集成了SendCloud邮件验证码模块
	* sword-common 通用工具模块
	* sword-core Sword扩展业务模块，增加了用户、文章、通知、Token以及发邮件、锁等服务。
	* sword-framework 核心框架模块，集成了Shiro权限管理，扩展了Redis缓存。
	* sword-generator 代码生成模块
	* sword-qrcode 二维码生成模块
	* sword-quartz 定时任务模块
	* sword-system 系统集成业务模块，包括用户、权限、菜单等基础业务
	* sword-upload 文件上传服务模块
	* sword-upload-aliyun 阿里云文件上传实现模块
	* sword-upload-tencent 腾讯云文件上传实现模块
	* sword-upload-local 本地文件上传实现模块
	
2. sword-module-root 启动类示例，admin为后台，app为前端RESTful API

	* sword-boot-admin 后台Boot示例
	* sword-boot-app 前端API Boot示例
	* sword-boot-generator 代码生成Boot示例
	
3. sword-module-deploy 发布配置示例
	
	* sword-deploy-admin 后台一键发布示例
	* sword-deploy-app 前端一键发布示例
	* sword-deploy-nginx Nginx一键发布示例
	
4. sword-module-mining 挖矿项目工具集

	* sword-mining-admin 挖矿项目后台模块
	* sword-mining-app 挖矿项目前端模块
	* sword-mining 挖矿项目基础API模块，扩展了钱包、充提、直推等用户基础数据
	* sword-mining-zbx 挖矿项目集成ZBX钱包模块 
	* sword-mining-wallet 挖矿项目基础钱包模块
	* sword-mining-wallet-zbx 挖矿项目对接ZBX钱包模块