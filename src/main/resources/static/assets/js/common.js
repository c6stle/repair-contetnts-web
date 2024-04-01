
//공통 페이징처리
function paging(pagingId, pagingInfo) {
    if (pagingInfo == null || pagingInfo.totalCnt === 0) {
        $('#' + pagingId).css('visibility', 'hidden');
    } else {
        let pagingObj = '';
        pagingObj += '<ul class="pagination" style="justify-content: center;">';

        pagingObj += '<li class="page-item"><a class="page-link" href="#">First Page</a></li>';
        pagingObj += '<li class="page-item">';
        pagingObj += '   <a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>';
        pagingObj += '</li>';

        for (let i = 0; i < pagingInfo.list.length; i++) {
            pagingObj += '<li class="page-item"><a class="page-link" href="#" onclick="fnSearch(' + pagingInfo.list[i] + ');">' + pagingInfo.list[i] + '</a></li>';
        }

        pagingObj += '<li class="page-item">';
        pagingObj += '   <a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&laquo;</span></a>';
        pagingObj += '</li>';

        pagingObj += '<li class="page-item"><a class="page-link" href="#">Last Page</a></li>';
        pagingObj += '</ul>';

        $('#' + pagingId).html(pagingObj).css('visibility', 'visible');
    }
}