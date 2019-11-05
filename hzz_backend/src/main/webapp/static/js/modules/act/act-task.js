/**
 *
 * @author: whuab_mc
 * @date: 2019-09-12 10:55:10:55
 * @version: V1.0
 */
var handleOptions = {
    dispatch: {
        hamletAudit:{

        },
        townAudit:{

        },
        countyAudit:{

        },
        cityAudit:{

        },
        provinceAudit:{

        }
    },
    handle: {
        hamletRiver: {

        },
        townRiver: {

        },
        countyRiver: {

        },
        cityRiver: {

        },
        provinceRiver: {

        }
    }
};

function handleDialog(index) {
    var row = $('#waitHandleList').datagrid('getData').rows[index];
    var title = '问题交办';
    var url = 'riverLakePatrolController/form.do?taskId='+row.taskId+'&taskName='+row.taskName+'&taskDefKey='+row.taskDefKey+'&taskExecutionId='+row.taskExecutionId+'&procInsId='+row.procInsId+'&procDefId='+row.procDefId;
    createwindow(title, url, 500, 400);
    /*debugger
    var param = JSON.stringify(row);
    var urlnew = 'riverLakePatrolController/form.do?act='+param;
    createwindow(title, decodeURIComponent(urlnew), 500, 400);*/
}

function readbpmnXml(taskName, row, index) {
    return '<a target="_blank" href="${webRoot}/act/diagram-viewer/index.html?processDefinitionId=' + row.procDefId + '&processInstanceId=' + row.procInsId + '">' + taskName + '</a>'
}


$(document).ready(function () {

});