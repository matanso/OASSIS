/**
 * Created by matan on 12/10/15.
 */


app.controller('navBarController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.loggedIn = false;
    $scope.crowdMode = false;
    $scope.queryMode = false;
    $scope.buttonText = "ok let's go";
    $scope.clickBig = function(){window.location.href = 'login.html'};
    function setCrowd(){
        $scope.crowdMode = true;
        $scope.queryMode = false;
        $scope.buttonText = "Query mode";
        $scope.clickBig = function(){$scope.func = setQuery()}
    }
    function setQuery(){
        $scope.crowdMode = false;
        $scope.queryMode = true;
        $scope.buttonText = "Crowd mode";
        $scope.clickBig = function(){$scope.func = setCrowd()}
    }
    $http.get('/app/loggedIn').then(function success(response) {
        if (response.data.success == 0) {
            if(response.data.loggedIn)
            {
                $scope.loggedIn = true;
                setCrowd();
            }
        }
    }, function error(response) {
    });
}]);