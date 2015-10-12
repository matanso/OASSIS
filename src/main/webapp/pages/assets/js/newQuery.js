/**
 * Created by matan on 12/10/15.
 */

var app = angular.module('newQueryApp', []);

app.controller('newQueryController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
     $scope.submit = function(){
         $http({
             url: '/app/submitQuery',
             method: "POST",
             headers: {'Content-Type': 'application/x-www-form-urlencoded'},
             data: $.param({name: $scope.name, sparql: $scope.query})
         }).then(function success(response){
             window.location.href = 'myQueries.html';
         }, function error(response){

         });
     }
}]);