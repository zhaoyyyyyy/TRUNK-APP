/**
 * ------------------------------------------------------------------
 * form 表单常用操作
 * ------------------------------------------------------------------
 */
var formFmt = (function (model){

        //开发版本号
        model.version = "1.0.0";
        model.author  = "xiaoyx";
        model.email   = "xiaoyx@asiainfo.com";

        /**
         * @description 将form 中的查询条件转化为json格式对象
         * @param {Jquery} [formObj] [form表单对象]
         * @return {Object} json 格式的对象
         * ------------------------------------------------------------------
         */
        
        model.formToObj = function(formObj) {
            var data={};  
            var array = $(formObj).serializeArray();
            //方法一  
          /*  $(array).each(function(){  
                    if(data[this.name]){  
                        if($.isArray(data[this.name])){  
                            data[this.name].push(this.value);  
                        }else{  
                            data[this.name]=[data[this.name],this.value];  
                        }  
                    }else{  
                        data[this.name]=this.value;   
                    }  
                });*/
            //方法二
            array.map(function(x){
              if (data[x.name] !== undefined) {
                        if (!data[x.name].push) {
                            data[x.name] = [data[x.name]];
                        }
                        data[x.name].push(x.value || '');
                    } else {
                        data[x.name] = x.value || '';
                    }
             });
            
            return data;  
        }

        return model;

   })(window.formFmt || {});