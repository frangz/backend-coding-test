Alchemy Code Test
=================

API Definition
-----------------------

This REST service is documented and mocked with Apiary.io. You can find the API documentation here: http://docs.alchemy.apiary.io/. The current mock URL is http://alchemy.apiary-mock.com/v1. You can make the front-end consume this mock by editing expenses.js, as following:

```javascript
var expensesApp = angular.module("expensesApp", ["restangular", "ui.date"]).config(function (RestangularProvider) {
	RestangularProvider.setBaseUrl('http://alchemy.apiary-mock.com/v1');
});
```

