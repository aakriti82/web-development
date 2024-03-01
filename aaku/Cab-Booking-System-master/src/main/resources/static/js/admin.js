
function msg() {
    document.getElementById("show").style.top = "0px";
}
function msg2() {
    document.getElementById("show").style.top = "-610px";
}



// hire aama
jQuery(document).ready(function($) {
    $(".mwb-form-control").focus(function(){
        var tmpThis = $(this).val();
        if(tmpThis == '' ) {
            $(this).parent(".mwb-form-group").addClass("focus-input");
        }
        else if(tmpThis !='' ){
            $(this).parent(".mwb-form-group").addClass("focus-input");
        }
    });
    $(".mwb-form-control").blur(function(){
        var tmpThis = $(this).val();
        if(tmpThis == '' ) {
            $(this).parent(".mwb-form-group").removeClass("focus-input");
            $(this).siblings('.mwb-form-error').slideDown("3000");
        }
        else if(tmpThis !='' ){
            $(this).parent(".mwb-form-group").addClass("focus-input");
            $(this).siblings('.mwb-form-error').slideUp("3000");

        }
    });

});



// admin dashboard
$(document).ready(function() {
    $(".section").hide();

    $(".secBtn").click(function() {
        $(".section").hide();
        $("." + $(this).attr("id").replace("Btn", "")).show();
        $(".secBtn").hide();
    });
});