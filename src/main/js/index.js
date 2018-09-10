import '../css/index.css';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Vue from 'vue';
// Ajax通信ライブラリ
import axios from 'axios';
// Json取得のベースURL
const URL_BASE = '/tasks/search/';

module.exports = new Vue({
-  data: {
-    // Jsonデータ格納用
-    search_list: []
-  }
});


new Vue({
    el: '#tasks',
     data: {
        name: 'Vue.js'
    },
    // `methods` オブジェクトの下にメソッドを定義する
    methods: {
      // Ajax通信でJsonを取得し、特定のプロパティに格納する
      // 取得したら GET_AJAX_COMPLETE で通知する
      ajax: function(event) {
        console.log("OK");
      }
    }
});