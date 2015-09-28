/**
 * Created by matan on 24/09/15.
 */

window.fbAsyncInit = function() {
    FB.init({
        appId      : '1626563104249095',
        xfbml      : true,
        version    : 'v2.4'
    });
};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));