import '../css/index.css';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Vue from 'vue';
// Ajax通信ライブラリ
import axios from 'axios';
// Json取得のベースURL
const URL_BASE = '/tasks/search/';

module.exports = new Vue({
    el: '#tasks',
     data: {
        url:"/tasks/search/",//v-model="url"の初期値
        param:"",             //v-model="param"の初期値
        result:"...."           //v-model="result"の初期値
    },

    methods: {
      // Ajax通信でJsonを取得し、特定のプロパティに格納する
      // 取得したら GET_AJAX_COMPLETE で通知する
      ajax: function(event) {
            let config = {
              headers:{
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type':'application / x-www-form-urlencoded'
              },
              withCredentials:true,
            }

          let param = this.param;

           axios.get("http://localhost:8080/tasks/search/?keyword=" + param)
                .then(function (response) {
                    console.log(response);
                    $("#list").replaceWith(response)
                })
                .catch(function (response) {
                    console.log(response);
                });
      }
    }
});