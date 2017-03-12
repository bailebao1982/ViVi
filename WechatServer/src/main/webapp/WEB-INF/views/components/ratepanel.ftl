<div id="rate_panel" class="rate_panel">
    <span id="rate_0" class="glyphicon glyphicon-star-empty rate_star"></span>
    <span id="rate_1" class="glyphicon glyphicon-star-empty rate_star"></span>
    <span id="rate_2" class="glyphicon glyphicon-star-empty rate_star"></span>
    <span id="rate_3" class="glyphicon glyphicon-star-empty rate_star"></span>
    <span id="rate_4" class="glyphicon glyphicon-star-empty rate_star"></span>
    <span id="rate_5" class="glyphicon glyphicon-star-empty rate_star"></span>
    <input id="rate_input" type="hidden" name="rate" value="">
</div>


<script type="application/javascript">
    $('#rate_panel').on('click', '.rate_star', function (event) {
        var rate = event.target.id.split("_")[1];
        var i = 0;
        for(; i <= rate; i++){
            var rate_star_id = "rate_" + i;
            $("#" + rate_star_id).removeClass("glyphicon-star-empty");
            $("#" + rate_star_id).addClass("glyphicon-star");
            $("#" + rate_star_id).css("color", "#FBCB44");
        }

        $("#rate_input").val(rate);

        for(; i <= 5; i++){
            var rate_star_id = "rate_" + i;
            $("#" + rate_star_id).removeClass("glyphicon-star");
            $("#" + rate_star_id).addClass("glyphicon-star-empty");
            $("#" + rate_star_id).css("color", "#000");
        }
    });
</script>