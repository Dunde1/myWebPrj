var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function (){
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-search').on('click', function () {
            _this.search();
        });
        $('#btn-comment').on('click', function () {
            _this.saveComment();
        });
    },
    save : function () {
        if(!this.formCheck($('#posts-title'), $('#posts-content'))){return}
        var data = {
            title: $('#posts-title').val(),
            author: $('#posts-author').val(),
            content: $('#posts-content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
          alert('글이 등록되었습니다.');
          window.location.href = '/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    },
    update : function () {
        if($('#post-name').val()!=$('#user-name').val()) {
            alert('글의 변경은 본인만 가능합니다.');
            return
        }

        if(!this.formCheck($('#posts-title'), $('#posts-content'))){return}

        var data = {
            title: $('#posts-title').val(),
            content: $('#posts-content').val()
        };

        var id = $('#posts-id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        if($('#post-name').val()!=$('#user-name').val()) {
            alert('글의 변경은 작성자만 가능합니다.');
            return
        }

        var id = $('#posts-id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    search : function () {
        var searchFilter = $('#searchFilter').val();
        var searchWord = $('#searchWord').val();

        if(!(searchFilter!="title"||searchFilter!="author")) searchFilter="none";

        if(!searchWord) {
            searchFilter="none"
            searchWord="none";
        }

        window.location.href = '/posts/list/'+searchFilter+'/'+searchWord+'/1';
    },
    formCheck : function (title, content) {
        if(title.val()==""){
            alert('제목을 입력하세요.');
            title.focus();
            return false
        }else if(content.val()==""){
            alert('내용을 입력하세요');
            content.focus();
            return false
        }
        return true
    },
    saveComment : function () {
        if($('#comment-content').val()=="") {
            alert("댓글 내용을 입력하세요.");
            $('#comment-content').focus();
            return
        }

        var data = {
            commentsId: {
                posts: {
                    id: $('#posts-id').val()
                }
            },
            comment: $('#comment-content').val(),
            author: $('#user-name').val()
        };

        var id = $('#posts-id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts/'+id+'/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('댓글이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }
};

main.init();

function updateComment(step) {

    if($('#commentAuthor'+step).val()!=$('#user-name').val()){
        alert("댓글 수정은 본인만 가능합니다.");
        return
    }

    var commentContent = $('#comment'+step);

    if(commentContent.val()=="") {
        alert("댓글 내용을 입력해주세요.");
        commentContent.focus();
        return
    }

    var data = {
        comment: commentContent.val()
    };

    var id = $('#posts-id').val();

    $.ajax({
        type: 'PUT',
        url: '/api/v1/posts/'+id+'/comments/'+step,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('댓글이 수정되었습니다.');
        window.location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error))
    });
}

function deleteComment(step) {

    if($('#commentAuthor'+step).val()!=$('#user-name').val()){
        alert("댓글 수정은 본인만 가능합니다.");
        return
    }

    var id = $('#posts-id').val();

    $.ajax({
        type: 'DELETE',
        url: '/api/v1/posts/'+id+'/comments/'+step,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('댓글이 삭제되었습니다.');
        window.location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error))
    });
}