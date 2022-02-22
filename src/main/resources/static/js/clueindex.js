function pageList(pageNo, pageSize) {
    $(".time").datetimepicker({
        minView: "month",
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

    var fullname=$.trim($("#hidden-fullname").val())
    var owner=$.trim($("#hidden-owner").val())
    var company=$.trim($("#hidden-company").val())
    var mphone=$.trim($("#hidden-mphone").val())
    var source=$.trim($("#hidden-source").val())
    var phone=$.trim($("#hidden-phone").val())
    var state=$.trim($("#hidden-state").val())


    $.ajax({

        url: "workbench/clue/pageList.do",
        data: {
            "fullname":fullname,
            "owner":owner,
            "company":company,
            "mphone":mphone,
            "source":source,
            "phone":phone,
            "state":state,
            "pageNo": pageNo,
            "pageSize": pageSize
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var html = ""
            $.each(data.list, function (i, n) {
                html+= '<tr>'
                html+= '<td><input name="xz" type="checkbox" value="'+n.id+'" /></td>'
                html+= '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/clue/detail.do?id='+n.id+'\';">'+n.fullname+'</a></td>'
                html+= '<td>'+n.company+'</td>'
                html+= '<td>'+n.mphone+'</td>'
                html+= '<td>'+n.phone+'</td>'
                html+= '<td>'+n.source+'</td>'
                html+= '<td>'+n.owner+'</td>'
                html+= '<td>'+n.state+'</td>'
                html+= '</tr>'
            })
            $("#clueBody").html(html)
            //数据处理完毕后，分页插件展现数据
            var totalPages = Math.floor((data.total + pageSize - 1) / pageSize)//整除操作

            $("#cluePage").bs_pagination({
                currentPage: pageNo, // 页码
                rowsPerPage: pageSize, // 每页显示的记录条数
                maxRowsPerPage: 20, // 每页最多显示的记录条数
                totalPages: totalPages, // 总页数
                totalRows: data.total, // 总记录条数
                visiblePageLinks: 3, // 显示几个卡片
                showGoToPage: true,
                showRowsPerPage: true,
                showRowsInfo: true,
                showRowsDefaultInfo: true,
                //该回调函数时在，点击分页组件的时候触发的
                onChangePage: function (event, data) {
                    pageList(data.currentPage, data.rowsPerPage);
                }
            });
        }

    })
}
function saveBtnClick() {

    $.ajax({

        url : "workbench/clue/save.do",
        data : $('#createForm').serialize(),
        type : "get",
        dataType : "json",
        success : function (data) {
            if(data){
                pageList($("#cluePage").bs_pagination('getOption', 'currentPage'),
                    $("#cluePage").bs_pagination('getOption', 'rowsPerPage'))
            }else {
                alert("保存失败")
            }
        }

    })
}

function createBtnClick() {
    var userId='[[${session.user.id}]]'
    $("#create-owner").val(userId)

    $("#createClueModal").modal("show")
}

function deleteBtnClick() {
    var $xz=$("input[name=xz]:checked")
    if($xz.length==0){
        alert("请选择至少一个线索")
        return
    }
    var param="ids="
    for (let i = 0; i <$xz.length ; i++) {
        if(i==$xz.length-1){
            param+=$($xz[i]).val()
        }else {
            param+=$($xz[i]).val()+","
        }
    }

    $.ajax({
        url : "workbench/clue/delete.do",
        data : param,
        type : "post",
        dataType : "json",
        success : function (data) {
            if(data){
                pageList(1, $("#cluePage").bs_pagination('getOption', 'rowsPerPage'))
            }else {
                alert("保存失败")
            }
        }

    })
}

function searchBtnClick() {

}