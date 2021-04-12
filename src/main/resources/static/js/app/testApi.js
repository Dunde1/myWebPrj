var test = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
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
    save: function () {
        if (!this.formCheck($('#posts-title'), $('#posts-content'))) {
            return
        }
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
    }
}

test.init();