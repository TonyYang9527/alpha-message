<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知中心测试页面</title>

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="assets/css/bootstrap.css" />
<link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="assets/css/font-awesome.css" />

<!-- text fonts -->
<link rel="stylesheet" href="assets/css/ace-fonts.css" />
<!-- ace styles -->
<link rel="stylesheet" href="assets/css/ace.css"
	class="ace-main-stylesheet" id="main-ace-style" />


</head>

<body class="no-skin">
	<!-- #section:basics/navbar.layout -->
	<div id="navbar" class="navbar navbar-default">

		<div class="navbar-container" id="navbar-container">
			<!-- /section:basics/sidebar.mobile.toggle -->
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
				<a href="#" class="navbar-brand"> <small> <i
						class="fa fa-leaf"></i> 通知中心测试页面
				</small>
				</a>
			</div>
		</div>
	</div>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- #section:basics/sidebar -->
		<div id="sidebar" class="sidebar responsive">
			<ul class="nav nav-list">
				<li class="active" id="sms_active_flg"><a href="javascript:;">
						<i class="menu-icon fa fa-comments"></i> <span class="menu-text">
							短信测试 </span>
				</a> <b class="arrow"></b></li>
				<li id="email_active_flg"><a href="javascript:;"> <i
						class="menu-icon fa fa-envelope"></i> <span class="menu-text">
							邮件测试 </span>
				</a> <b class="arrow"></b></li>
				<li id="push_active_flg"><a href="javascript:;"> <i
						class="menu-icon fa fa-bell-o"></i> <span class="menu-text">
							PUSH通知 </span>
				</a> <b class="arrow"></b></li>
				<li id="siteMsg_active_flg"><a href="javascript:;"> <i
						class="menu-icon fa fa-lemon-o"></i> <span class="menu-text">
							站内信 </span>
				</a> <b class="arrow"></b></li>
			</ul>
			<!-- /.nav-list -->
		</div>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">通知中心测试页面</a>
						</li>
						<li class="active" id="sms_info">短信</li>
						<li class="active" id="email_info">邮件</li>
						<li class="active" id="push_info">PUSH</li>
						<li class="active" id="siteMsg_info">站内信</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="row" id="sms_content">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" id="form">
								<!-- 模板ID -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										模板ID： </label>
									<div class="col-sm-9">
										<input type="text" id="sms_templateId" name="templateId"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<div class="form-group" id="templateDiv">
									<label class="col-sm-3 control-label no-padding-right">
										模板内容： </label>
									<div class="col-sm-9">
										<textarea style="height: 200px;" id="template"
											readonly="readonly" disabled="disabled"
											class="col-xs-10 col-sm-5"></textarea>
									</div>
								</div>

								<!-- Key/Value -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										Key/Value： </label>
									<div class="col-sm-9">
										<textarea class="col-xs-10 col-sm-5" style="height: 100px;"
											id="keyValue" name="keyValue"></textarea>
									</div>
								</div>

								<!-- 手机号码 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										手机号码： </label>
									<div class="col-sm-9">
										<textarea style="height: 300px;" id="mobiles" name="mobiles"
											class="col-xs-10 col-sm-5"></textarea>
									</div>
								</div>

								<!-- 发送时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										发送时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="scheduleUtcTime" name="scheduleUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii" />
									</div>
								</div>

								<!-- 过期时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										过期时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="expiredUtcTime" name="expiredUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii"
											data-link-field="expiredUtcTime">
									</div>
								</div>

								<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info" type="button" id="submit">
										<i class="ace-icon fa fa-check bigger-110"></i> 提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset">
										<i class="ace-icon fa fa-undo bigger-110"></i> 重置
									</button>
								</div>

							</form>
						</div>
					</div>

					<div class="row" id="email_content">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" id="email_form"
								enctype="multipart/form-data">
								<!-- 模板ID -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										模板ID： </label>
									<div class="col-sm-9">
										<input type="text" id="email_templateId" name="templateId"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<div class="form-group" id="email_templateDiv">
									<label class="col-sm-3 control-label no-padding-right">
										模板内容： </label>
									<div class="col-sm-9">
										<textarea style="height: 200px;" id="email_template"
											readonly="readonly" disabled="disabled"
											class="col-xs-10 col-sm-5"></textarea>
									</div>
								</div>

								<!-- Key/Value -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										Key/Value： </label>
									<div class="col-sm-9">
										<textarea class="col-xs-10 col-sm-5" style="height: 100px;"
											id="email_keyValue" name="keyValue"></textarea>
									</div>
								</div>

								<!-- 邮件地址 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										邮件地址： </label>
									<div class="col-sm-9">
										<input type="text" id="toAddresses" name="toAddresses"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>
								<!-- 抄送地址 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										抄送地址： </label>
									<div class="col-sm-9">
										<input type="text" id="ccAddresses" name="ccAddresses"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>
								<!-- 暗送地址 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										暗送地址： </label>
									<div class="col-sm-9">
										<input type="text" id="bccAddresses" name="bccAddresses"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<!-- 发送时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										发送时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="emai_scheduleUtcTime" name="scheduleUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii" />
									</div>
								</div>

								<!-- 过期时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										过期时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="emai_expiredUtcTime" name="expiredUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii"
											data-link-field="emai_expiredUtcTime">
									</div>
								</div>

								<!-- 附件内容 
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 附件内容： </label>

										<div class="col-sm-9">
											<input type="file" id="file" name="file" class="col-xs-10 col-sm-5" multiple />
										</div>
									</div>-->

								<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info" type="button" id="email_submit">
										<i class="ace-icon fa fa-check bigger-110"></i> 提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset">
										<i class="ace-icon fa fa-undo bigger-110"></i> 重置
									</button>
								</div>

							</form>
						</div>
					</div>


					<div class="row" id="push_content">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" role="form" id="push_form"
								enctype="multipart/form-data">
								<!-- 模板ID -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										模板ID： </label>
									<div class="col-sm-9">
										<input type="text" id="push_templateId" name="templateId"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<div class="form-group" id="push_templateDiv">
									<label class="col-sm-3 control-label no-padding-right">
										模板内容： </label>
									<div class="col-sm-9">
										<textarea style="height: 200px;" id="push_template"
											readonly="readonly" disabled="disabled"
											class="col-xs-10 col-sm-5"></textarea>
									</div>
								</div>

								<!-- Key/Value -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										Key/Value： </label>
									<div class="col-sm-9">
										<textarea class="col-xs-10 col-sm-5" style="height: 100px;"
											id="push_keyValue" name="keyValue"></textarea>
									</div>
								</div>

								<!-- 接受方ID -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										接受方ID： </label>
									<div class="col-sm-9">
										<input type="text" id="receiverId" name="receiverId"
											class="col-xs-10 col-sm-5" />
									</div>
								</div>

								<!-- 发送时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										发送时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="push_scheduleUtcTime" name="scheduleUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii" />
									</div>
								</div>

								<!-- 过期时间 -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right">
										过期时间： </label>
									<div class="col-sm-9">
										<input class="time-picker col-xs-10 col-sm-5"
											id="push_expiredUtcTime" name="expiredUtcTime" type="text"
											data-date-format="yyyy-mm-dd hh:ii"
											data-link-field="emai_expiredUtcTime">
									</div>
								</div>

								<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info" type="button" id="push_submit">
										<i class="ace-icon fa fa-check bigger-110"></i> 提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset">
										<i class="ace-icon fa fa-undo bigger-110"></i> 重置
									</button>
								</div>

							</form>
						</div>
					</div>


					<div class="row" id="siteMsg_content">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12 col-sm-6 widget-container-col">
									<!-- #section:custom/widget-box -->
									<div class="widget-box">
										<div class="widget-header">
											<h5 class="widget-title">发送站内信</h5>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<form class="form-horizontal" role="form" id="siteMsg_form">
													<!-- 模板ID -->
													<div class="form-group">
														<label class="col-sm-3 control-label no-padding-right">
															模板ID： </label>
														<div class="col-sm-9">
															<input type="text" id="siteMsg_templateId" name="templateId"
																class="col-xs-10 col-sm-10" />
														</div>
													</div>

													<div class="form-group" id="siteMsg_templateDiv">
														<label class="col-sm-3 control-label no-padding-right">
															模板内容： </label>
														<div class="col-sm-9">
															<textarea style="height: 150px;" id="siteMsg_template"
																readonly="readonly" disabled="disabled"
																class="col-xs-10 col-sm-10"></textarea>
														</div>
													</div>

													<!-- Key/Value -->
													<div class="form-group">
														<label class="col-sm-3 control-label no-padding-right">
															Key/Value： </label>
														<div class="col-sm-9">
															<textarea class="col-xs-10 col-sm-10"
																style="height: 100px;" id="siteMsg_keyValue"
																name="keyValue"></textarea>
														</div>
													</div>

													<!-- 接受方ID -->
													<div class="form-group">
														<label class="col-sm-3 control-label no-padding-right">
															接受方ID： </label>
														<div class="col-sm-9">
															<input type="text" id="siteMsg_receiver" name="receiver"
																class="col-xs-10 col-sm-10" />
														</div>
													</div>

													<!-- 发送时间 -->
													<div class="form-group">
														<label class="col-sm-3 control-label no-padding-right">
															发送时间： </label>
														<div class="col-sm-9">
															<input class="time-picker col-xs-10 col-sm-10"
																id="siteMsg_scheduleUtcTime" name="scheduleUtcTime"
																type="text" data-date-format="yyyy-mm-dd hh:ii" />
														</div>
													</div>

													<!-- 过期时间 -->
													<div class="form-group">
														<label class="col-sm-3 control-label no-padding-right">
															过期时间： </label>
														<div class="col-sm-9">
															<input class="time-picker col-xs-10 col-sm-10"
																id="siteMsg_expiredUtcTime" name="expiredUtcTime"
																type="text" data-date-format="yyyy-mm-dd hh:ii"
																data-link-field="siteMsg_expiredUtcTime" />
														</div>
													</div>

													<div class="form-group">
														<div class="col-md-offset-3 col-md-9">
															<button class="btn btn-info" type="button"
																id="siteMsg_submit">
																<i class="ace-icon fa fa-check bigger-110"></i> 提交
															</button>

															&nbsp; &nbsp; &nbsp;
															<button class="btn" type="reset">
																<i class="ace-icon fa fa-undo bigger-110"></i> 重置
															</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>

								<div class="col-xs-12 col-sm-6 widget-container-col">
									<form class="form-horizontal" role="form" id="siteMsgSearch_form">
										<!-- 用户ID -->
										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right">
												用户ID： </label>
											<div class="col-sm-9">
												<input type="text" id="searchUserId" name="userId"
													class="col-xs-10 col-sm-10" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-offset-3 col-md-9">
												<button class="btn btn-info" type="button"
													id="searchSubmit">
													<i class="ace-icon fa fa-check bigger-110"></i> 查询
												</button>
											</div>
										</div>
									</form>
								
									<div class="widget-box widget-color-blue">
										<!-- #section:custom/widget-box.options -->
										<div class="widget-header">
											<h5 class="widget-title bigger lighter">
												<i class="ace-icon fa fa-table"></i> 查询站内信
											</h5>
										</div>

										<!-- /section:custom/widget-box.options -->
										<div class="widget-body">
											<div class="widget-main no-padding">
												<table id="siteMsgTable"
													class="table table-striped table-bordered table-hover">
													<thead class="thin-border-bottom">
														<tr>
															<th>用户</th>

															<th>站内信</th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<!-- /.span -->
							</div>
							<!-- /.row -->
						</div>
					</div>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<script src="assets/js/jquery.js"></script>
	<script src="assets/js/bootstrap.js"></script>


	<script src="assets/js/date-time/bootstrap-datetimepicker.min.js"></script>
	<script src="assets/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>

	<script>
		$(document).ready(
				function() {
					//格式化时间
					$('.time-picker').datetimepicker({
						language : 'zh-CN',
						autoclose : 1,
						todayBtn : 1,
						todayHighlight : 1,
						forceParse : 0
					});

					//短信模板内容隐藏
					$("#templateDiv").hide();
					//邮件模板内容隐藏
					$("#email_templateDiv").hide();
					//push模板内容的隐藏
					$("#push_templateDiv").hide();
					//站内信模板内容的隐藏
					$("#siteMsg_templateDiv").hide();
					//获取短信模板
					$("#sms_templateId").blur(function() {
						var templateId = this.value;
						$.ajax({
							type : 'get',
							url : 'api/getSmsTemplate',
							data : {
								id : templateId
							},
							dataType : 'json',
							success : function(data) {
								var template = data.template;
								var keyValue = data.keyValue;
								$("#template").val(template);
								$("#templateDiv").fadeIn("slow");
								$("#keyValue").val(keyValue)
							},
							error : function(data) {
								alert("查询模板错误");
							}
						});
					});

					//获取邮件模板
					$("#email_templateId").blur(function() {
						var templateId = this.value;
						$.ajax({
							type : 'get',
							url : 'api/getEmailTemplate',
							data : {
								id : templateId
							},
							dataType : 'json',
							success : function(data) {
								var template = data.template;
								var keyValue = data.keyValue;
								$("#email_template").val(template);
								$("#email_templateDiv").fadeIn("slow");
								$("#email_keyValue").val(keyValue)
							},
							error : function(data) {
								alert("查询模板错误");
							}
						});
					});

					//获取push模板
					$("#push_templateId").blur(function() {
						var templateId = this.value;
						$.ajax({
							type : 'get',
							url : 'api/getPushTemplate',
							data : {
								id : templateId
							},
							dataType : 'json',
							success : function(data) {
								var template = data.template;
								var keyValue = data.keyValue;
								$("#push_template").val(template);
								$("#push_templateDiv").fadeIn("slow");
								$("#push_keyValue").val(keyValue)
							},
							error : function(data) {
								alert("查询模板错误");
							}
						});
					});
					
					//获取站内信模板
					$("#siteMsg_templateId").blur(function() {
						var templateId = this.value;
						$.ajax({
							type : 'get',
							url : 'api/getSiteMsgTemplate',
							data : {
								id : templateId
							},
							dataType : 'json',
							success : function(data) {
								var template = data.template;
								var keyValue = data.keyValue;
								$("#siteMsg_template").val(template);
								$("#siteMsg_templateDiv").fadeIn("slow");
								$("#siteMsg_keyValue").val(keyValue)
							},
							error : function(data) {
								alert("查询模板错误");
							}
						});
					});

					//菜单的切换
					$("#email_content").hide();
					$("#email_info").hide();
					$("#push_content").hide();
					$("#push_info").hide();
					$("#siteMsg_content").hide();
					$("#siteMsg_info").hide();

					$("#email_active_flg").click(function() {
						$("#email_active_flg").addClass("active");
						$("#sms_active_flg").removeClass();
						$("#push_active_flg").removeClass();
						$("#siteMsg_active_flg").removeClass();

						$("#email_content").show();
						$("#email_info").show();
						$("#sms_content").hide();
						$("#sms_info").hide();
						$("#push_content").hide();
						$("#push_info").hide();
						$("#siteMsg_content").hide();
						$("#siteMsg_info").hide();
					});

					$("#sms_active_flg").click(function() {
						$("#sms_active_flg").addClass("active");
						$("#email_active_flg").removeClass();
						$("#push_active_flg").removeClass();
						$("#siteMsg_active_flg").removeClass();

						$("#sms_content").show();
						$("#sms_info").show();
						$("#email_content").hide();
						$("#email_info").hide();
						$("#push_content").hide();
						$("#push_info").hide();
						$("#siteMsg_content").hide();
						$("#siteMsg_info").hide();
					});

					$("#push_active_flg").click(function() {
						$("#push_active_flg").addClass("active");
						$("#sms_active_flg").removeClass();
						$("#email_active_flg").removeClass();
						$("#siteMsg_active_flg").removeClass();

						$("#push_content").show();
						$("#push_info").show();
						$("#sms_content").hide();
						$("#sms_info").hide();
						$("#email_content").hide();
						$("#email_info").hide();
						$("#siteMsg_content").hide();
						$("#siteMsg_info").hide();
					});

					$("#siteMsg_active_flg").click(function() {
						$("#siteMsg_active_flg").addClass("active");
						$("#sms_active_flg").removeClass();
						$("#email_active_flg").removeClass();
						$("#push_active_flg").removeClass();
						
						$("#siteMsg_content").show();
						$("#siteMsg_info").show();
						$("#push_content").hide();
						$("#push_info").hide();
						$("#sms_content").hide();
						$("#sms_info").hide();
						$("#email_content").hide();
						$("#email_info").hide();

					});

					$("#addFile_001").click(
							function() {
								var id_tmp = "#files_001";
								var array_tmp;
								var fileUpload_id = "files_001"
										+ "_fileUpload_";

								if ($(id_tmp + " > input") != undefined) {
									array_tmp = $(id_tmp + " > input")
											.toArray().length;
									if (array_tmp != 0) {
										array_tmp = array_tmp + 1;
										var tp = "<input type='file' id='"
												+ fileUpload_id + (array_tmp)
												+ "' name='file'"
												+ " width='40'><br>"
										$(id_tmp).append(tp);
									} else {
										array_tmp = 1;
										var tp = "<input type='file' id='"
												+ fileUpload_id + (array_tmp)
												+ "' name='file'"
												+ " width='40'><br>"
										$(id_tmp).append(tp);
									}

								}
								return false;
							});

					//请求后台sms
					$("#submit").click(function() {
						var data = $("#form").serialize();
						$.ajax({
							type : 'post',
							url : 'api/sendSms',
							data : data,
							dataType : 'json',
							success : function(data) {
								alert(data);
							}
						});
					});

					//请求后台email
					$("#email_submit").click(function() {
						var data = $("#email_form").serialize();
						$.ajax({
							type : 'post',
							url : 'api/sendEmail',
							data : data,
							dataType : 'json',
							success : function(data) {
								alert(data);
							}
						});
					});

					//请求后台push
					$("#push_submit").click(function() {
						var data = $("#push_form").serialize();
						$.ajax({
							type : 'post',
							url : 'api/sendPush',
							data : data,
							dataType : 'json',
							success : function(data) {
								alert(data);
							}
						});
					});
					
					//请求后台siteMsg
					$("#siteMsg_submit").click(function() {
						var data = $("#siteMsg_form").serialize();
						$.ajax({
							type : 'post',
							url : 'api/sendSiteMsg',
							data : data,
							dataType : 'json',
							success : function(data) {
								alert(data);
							}
						});
					});
					
					$("#searchSubmit").click(function() {
						var data = $("#siteMsgSearch_form").serialize();
						$.ajax({
							type : 'get',
							url : 'api/getSiteMsg',
							data : data,
							dataType : 'json',
							success : function(data) {
								$("#siteMsgTable > tbody").html("");
								$.each(data, function(i){  
									$("#siteMsgTable > tbody").append("<tr><td>" + data[i].receiverId + "</td><td>" + data[i].siteMessageContent + "</td></tr>");
								});
							}
						});
					});
					
					

				});

		addFileJs = function(id) {
			var id_tmp = "#" + id;
			var array_tmp;
			var fileUpload_id = id + "_fileUpload_";

			if ($(id_tmp + " > input") != undefined) {
				array_tmp = $(id_tmp + " > input").toArray().length;
				if (array_tmp != 0) {
					array_tmp = array_tmp + 1;
					var tp = "<input type='file' id='" + fileUpload_id
							+ (array_tmp) + "' name='file'"
							+ " width='40'><br>"
					$(id_tmp).append(tp);
				} else {
					array_tmp = 1;
					var tp = "<input type='file' id='" + fileUpload_id
							+ (array_tmp) + "' name='file'"
							+ " width='40'><br>"
					$(id_tmp).append(tp);
				}

			}
		}
	</script>
</body>

</html>