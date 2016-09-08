<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- END THEME GLOBAL STYLES -->
<link href="../assets/global/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/global/plugins/fancytree/dist/themes/skin-bootstrap/ui.fancytree.min.css" rel="stylesheet" type="text/css" />

<table class="table table-bordered tree-table">
    <thead>
    <tr>
        <th style="width: 46px;"></th>
        <th style="width: 80px;">#</th>
        <th>Items</th>
        <th style="width: 80px;">Key</th>
        <th style="width: 46px;">Like</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    </tbody>
</table>

<script src="../assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>

<script src="../assets/global/plugins/fancytree/dist/jquery.fancytree.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/fancytree/dist/jquery.fancytree-all.min.js" type="text/javascript"></script>
<!-- END THEME LAYOUT SCRIPTS -->
<script type="text/javascript">
    glyph_opts = {
        map: {
            doc: "glyphicon glyphicon-file",
            docOpen: "glyphicon glyphicon-file",
            checkbox: "glyphicon glyphicon-unchecked",
            checkboxSelected: "glyphicon glyphicon-check",
            checkboxUnknown: "glyphicon glyphicon-share",
            dragHelper: "glyphicon glyphicon-play",
            dropMarker: "glyphicon glyphicon-arrow-right",
            error: "glyphicon glyphicon-warning-sign",
            expanderClosed: "glyphicon glyphicon-menu-right",
            expanderLazy: "glyphicon glyphicon-menu-right",  // glyphicon-plus-sign
            expanderOpen: "glyphicon glyphicon-menu-down",  // glyphicon-collapse-down
            folder: "glyphicon glyphicon-folder-close",
            folderOpen: "glyphicon glyphicon-folder-open",
            loading: "glyphicon glyphicon-refresh glyphicon-spin"
        }
    };
    $(function(){

        $(".tree-table").fancytree({
            extensions: ["edit", "glyph", "wide","table"],// ,"dnd"
            checkbox: true,
            dnd: {
                focusOnClick: true,
                dragStart: function(node, data) { return true; },
                dragEnter: function(node, data) { return false; },
                dragDrop: function(node, data) { data.otherNode.copyTo(node, data.hitMode); }
            },
            glyph: glyph_opts,
            wide: {
                iconWidth: "1em",     // Adjust this if @fancy-icon-width != "16px"
                iconSpacing: "0.5em", // Adjust this if @fancy-icon-spacing != "3px"
                levelOfs: "1.5em"     // Adjust this if ul padding != "16px"
            },
            table: {
                indentation: 20,      // indent 20px per node level
                nodeColumnIdx: 2,     // render the node title into the 2nd column
                checkboxColumnIdx: 0  // render the checkboxes into the 1st column
            },
            source: {
                url: "demo/data/fancytree/fancytree.json"
            },
            lazyLoad: function(event, data) {
                data.result = {url: "data/fancytree/ajax-sub2.json"}
            },
            renderColumns: function(event, data) {
                var node = data.node,
                        $tdList = $(node.tr).find(">td");

                // (index #0 is rendered by fancytree by adding the checkbox)
                $tdList.eq(1).text(node.getIndexHier()).addClass("alignRight");

                // (index #2 is rendered by fancytree)
                $tdList.eq(3).text(node.key);
                $tdList.eq(4).addClass('text-center').html("<input type='checkbox' class='styled' name='like' value='" + node.key + "'>");

                // Style checkboxes
                $(".styled").uniform({radioClass: 'choice'});
            },
            activate: function(event, data) {
                console.log(data);
            }
        });
        $("#btnExpandAll").click(function(){
            $(".tree-table").fancytree("getTree").visit(function(node){
                node.setExpanded(true);
            });
        });
        $("#btnCollapseAll").click(function(){
            $(".tree-table").fancytree("getTree").visit(function(node){
                node.setExpanded(false);
            });
        });

        $( "#fontSize" ).change(function(){
            $(".tree-table .fancytree-container").css("font-size", $(this).prop("value") + "pt");
        });//.prop("value", 12);

        $("#plainTreeStyles").on("change", "input", function(e){
            $(".tree-table ul").toggleClass($(this).data("class"), $(this).is(":checked"));
        });
        $("#bootstrapTableStyles").on("change", "input", function(e){
            $(".tree-table").toggleClass($(this).data("class"), $(this).is(":checked"));
        });
    });
</script>
<div class="panel-footer">
    <button id="btnExpandAll" class="btn btn-xs btn-primary">Expand all</button>
    <button id="btnCollapseAll" class="btn btn-xs btn-warning">Collapse all</button>
</div>
<p id="bootstrapTableStyles">
    <b>Add table class:</b><br>
    <label><input type="checkbox" data-class="table-bordered"> table-bordered</label>
    <label><input type="checkbox" data-class="table-condensed" checked="checked"> table-condensed</label>
    <label><input type="checkbox" data-class="table-striped" checked="checked"> table-striped</label>
    <label><input type="checkbox" data-class="table-hover" checked="checked"> table-hover</label>
    <label><input type="checkbox" data-class="table-responsive"> table-responsive</label>
    <label><input type="checkbox" data-class="fancytree-colorize-selected"> fancytree-colorize-selected</label>
</p>