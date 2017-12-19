(function () {
    'use strict';

    angular
        .module('app')
        .controller('BookingsController', BookingsController);

    BookingsController.$inject = ['$http'];

    function BookingsController($http) {
        var vm = this;

        vm.schedules = [];
        vm.getSouthAll = getSouthAll;
        vm.getNorthAll = getNorthAll;
        //
        init();

        function init(){
            getSouthAll();
        }

        function getSouthAll(){
            var url = "/getSchedule/SouthAll";
            var southAll = $http.get(url);
            southAll.then(function(response){
                vm.schedules = response.data;
            });
        }

        function getNorthAll(){
            var url = "/getSchedule/NorthAll";
            var northAll = $http.get(url);
            northAll.then(function(response){
                vm.schedules = response.data;
            });
        }
    }
})();
