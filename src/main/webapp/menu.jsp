<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <!-- BEGIN SIDEBAR -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="padding-top: 20px">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler"> </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
            <li class="sidebar-search-wrapper">
                <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
                <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
                <form class="sidebar-search  sidebar-search-bordered" action="page_general_search_3.html" method="POST">
                    <a href="javascript:;" class="remove">
                        <i class="icon-close"></i>
                    </a>
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                                                        <a href="javascript:;" class="btn submit">
                                                            <i class="icon-magnifier"></i>
                                                        </a>
                                                    </span>
                    </div>
                </form>
                <!-- END RESPONSIVE QUICK SEARCH FORM -->
            </li>
            <li class="nav-item start active open">
                <a href="dashboard.jsp" class="nav-link ajaxify">
                    <i class="icon-home"></i>
                    <span class="title">首页</span>
                </a>
            </li>
            <li class="nav-item">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title">测试页面</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item">
                        <a href="test/webqq.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">WebSocket</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="test/test.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">表单提交实体集合</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="test/test.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">前后数据交互</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="test/jcseg.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">中文分词器</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="test/word2vec.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">word2vec</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="test/tsne.jsp" class="nav-link ajaxify">
                            <i class="icon-bar-chart"></i>
                            <span class="title">tsne</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item last">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-layers"></i>
                    <span class="title">末尾菜单</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item last">
                        <a href="test/fancytree.jsp" class="nav-link ajaxify">
                            <span class="title">树状表格</span>
                        </a>
                    </li>
                    <li class="nav-item last">
                        <a href="sys/sysUser.jsp" class="nav-link ajaxify">
                            <span class="title">用户管理</span>
                        </a>
                    </li>
                </ul>
            </li>

        </ul>
        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
<!-- END SIDEBAR -->
<script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
<script type="text/javascript">
    (function($, undefined) {
        $(function () {
            $("li.active>a").click();
        });
    })(window.jQuery);
</script>