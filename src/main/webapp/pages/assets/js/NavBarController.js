/**
 * Created by matan on 12/10/15.
 */


app.controller('navBarController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    var always_buttons = [{class: "current", href: "index.html", text: "Home"},
        {href: "index.html#about", text: "About"},
        {href: "index.html#main-wrapper", text: "Contribute"}];
    var query_buttons = [{href: "newQuery.html", text: "Submit query"},
        {href: "myQueries.html", text: "My queries"}];
    var crowd_buttons = [
        {href:"question.html", text: "Answer questions"},
        {href: "awards.html", text: "Awards"},
        {href: "Profile.html", text: "My profile"}];
    var login_button = [{href: "logout.html", text: "Logout"}];
    var logout_button = [{href: "login.html", text: "Login"}];
    $scope.loggedIn = false;
    $scope.crowdMode = sessionStorage.getItem("mode") == "crowd";
    $scope.queryMode = sessionStorage.getItem("mode") == "query";
    $scope.buttonText = "ok let's go";
    $scope.buttons = always_buttons + login_button;
    console.log($scope.buttons);
    $scope.clickBig = function () {
        window.location.href = 'login.html'
    };
    function setCrowd() {
        sessionStorage.setItem("mode", "crowd");
        $scope.crowdMode = true;
        $scope.queryMode = false;
        $scope.buttons = always_buttons + crowd_buttons + logout_button;
        $scope.buttonText = "Query mode";
        $scope.clickBig = function () {
            $scope.func = setQuery()
        }
    }

    function setQuery() {
        sessionStorage.setItem("mode", "query");
        $scope.crowdMode = false;
        $scope.queryMode = true;
        $scope.buttons = always_buttons + query_buttons + logout_button;
        $scope.buttonText = "Crowd mode";
        $scope.clickBig = function () {
            $scope.func = setCrowd()
        }
    }

    $http.get('/app/loggedIn').then(function success(response) {
        if (response.data.success == 0) {
            if (response.data.loggedIn) {
                $scope.loggedIn = true;
                setCrowd();
            }
        }
    }, function error(response) {
    });
}]);