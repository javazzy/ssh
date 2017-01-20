<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <base href="${pageContext.request.contextPath}/">
    <meta charset="utf-8" />
    <title>SSH</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="assets/global/fonts/fonts.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->

    <link href="assets/global/plugins/bootstrap-sweetalert/sweetalert.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />

    <link href="assets/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />

    <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />

    <!-- Datatables 表格控件样式以及相关插件样式 -->
    <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/datatables/plugins/responsive/datatables.responsive.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/datatables/plugins/responsive/responsive.bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
    <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />

    <link href="assets/global/css/common.css" rel="stylesheet" type="text/css" />

    <!-- END THEME LAYOUT STYLES -->
    <link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-footer-fixed page-sidebar-fixed">
    <div class="page-wrapper">

        <jsp:include page="header.jsp"/>
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->

        <jsp:include page="container.jsp"/>

        <jsp:include page="footer.jsp"/>
    </div>
    <!-- BEGIN QUICK NAV -->
    <%--<nav class="quick-nav">--%>
        <%--<a class="quick-nav-trigger" href="#0">--%>
            <%--<span aria-hidden="true"></span>--%>
        <%--</a>--%>
        <%--<ul>--%>
            <%--<li>--%>
                <%--<a href="https://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes" target="_blank" class="active">--%>
                    <%--<span>Purchase Metronic</span>--%>
                    <%--<i class="icon-basket"></i>--%>
                <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="https://themeforest.net/item/metronic-responsive-admin-dashboard-template/reviews/4021469?ref=keenthemes" target="_blank">--%>
                    <%--<span>Customer Reviews</span>--%>
                    <%--<i class="icon-users"></i>--%>
                <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="http://keenthemes.com/showcast/" target="_blank">--%>
                    <%--<span>Showcase</span>--%>
                    <%--<i class="icon-user"></i>--%>
                <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="http://keenthemes.com/metronic-theme/changelog/" target="_blank">--%>
                    <%--<span>Changelog</span>--%>
                    <%--<i class="icon-graph"></i>--%>
                <%--</a>--%>
            <%--</li>--%>
        <%--</ul>--%>
        <%--<span aria-hidden="true" class="quick-nav-bg"></span>--%>
    <%--</nav>--%>
    <%--<div class="quick-nav-overlay"></div>--%>
    <!-- END QUICK NAV -->
    <!--[if lt IE 9]>
    <script src="assets/global/plugins/respond.min.js"></script>
    <script src="assets/global/plugins/excanvas.min.js"></script>
    <script src="assets/global/plugins/ie8.fix.min.js"></script>
    <![endif]-->
    <!-- BEGIN CORE PLUGINS -->
    <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
    <!-- END CORE PLUGINS -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <%--<script src="assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>--%>
    <script src="assets/global/plugins/bootstrap-sweetalert/sweetalert.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>

    <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

    <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>

    <script src="assets/global/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="assets/global/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>

    <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>

    <script src="assets/global/plugins/jquery.form.js" type="text/javascript"></script>
    <script src="assets/global/plugins/Base64.js" type="text/javascript"></script>

    <!-- Datatables 表格控件以及相关插件 -->
    <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/plugins/responsive/datatables.responsive.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/plugins/responsive/responsive.bootstrap.min.js" type="text/javascript"></script>

    <!-- END PAGE LEVEL PLUGINS -->
    <!-- BEGIN THEME GLOBAL SCRIPTS -->
    <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
    <!-- END THEME GLOBAL SCRIPTS -->
    <!-- BEGIN THEME LAYOUT SCRIPTS -->
    <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
    <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
    <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
    <script src="assets/layouts/global/scripts/quick-nav.min.js" type="text/javascript"></script>
    <!-- END THEME LAYOUT SCRIPTS -->
    <%--<script src="assets/global/plugins/commonSetting.js" type="text/javascript"></script>--%>
    <script src="assets/global/scripts/icons.js" type="text/javascript"></script>
    <script src="assets/global/scripts/commonSetting.js" type="text/javascript"></script>
    <script src="assets/global/scripts/alerts.js" type="text/javascript"></script>

    <script type="text/javascript">
        var basepath = "${pageContext.request.contextPath}/";
        (function($, undefined) {
            $(function () {
                $("li.active>a").click();
            });
        })(window.jQuery);
    </script>
</body>

</html>