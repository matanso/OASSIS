/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('viewQueryApp', []);
app.controller('queryController', ['$scope', '$sce', '$http', '$location', function ($scope, $sce, $http, $location) {
    $scope.query = {};
    var queryId = sessionStorage.getItem('queryId');
    if(!queryId)
    {
        window.location.href = 'index.html';
        return;
    }
    $http.get('/app/getQuery?queryId=' + queryId).then(function success(response){$scope.query=response.data}, function error(response){});
}]);