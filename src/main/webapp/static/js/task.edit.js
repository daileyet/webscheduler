(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Select_Tasktype':'#tasktype',
			'Optionselected_Tasktype':'#tasktype option:selected',
			'Div_Tasktype_Ref':'#tasktype-ref',
			'Txtarea_Taskref':'#taskref',
			'Btn_Ref_example':'#taskref-toolbar button[data-role="example"]',
			'Btn_Ref_copy':'#taskref-toolbar button[data-role="copy"]',
			'Btn_Ref_clear':'#taskref-toolbar button[data-role="clear"]',
		init:function(){
			this.components.init();
		}
	};
	ctx.VIEW.components = {
			init:function(){
				this.refEditor.init();
			},
			refEditor : {
				init:function(){
					this.$txtarea = $(ctx.VIEW.Txtarea_Taskref);
					
					var _this =this;
					var editor = CodeMirror.fromTextArea($(ctx.VIEW.Txtarea_Taskref)[0], {
					    lineNumbers: true,
					    lineWrapping: true
					  });
					editor.on("change",function(instance,changeObj){
				    	var sNew = instance.doc.getValue();
				    	_this.$txtarea.val(sNew);
				    });
					this.cm  = editor;
					/*this.$txtarea.css({
					//"visibility" : "hidden",
					"position" : "absolute",
					"display" : "inline",
					"width" : "0",
					"margin-left" : "30px",
					"height" : "0",
					"padding" : "0px",
					"border-width" : "0"
				});*/
					this.$txtarea.appendTo($('.CodeMirror'));
				},
				setContent:function(str){
					if(this.cm){
						this.cm.doc.setValue(str);
					}else{
						this.$txtarea.val(str);
					}
				},
				getContent:function(){
					if(this.cm){
						return this.cm.doc.getValue();
					}else{
						return this.$txtarea.val();
					}
				},
				setRequired:function(isRequired){
					this.$txtarea.prop("required",isRequired);
				}
			}
	}
	
	//Model
	ctx.MODEL = {
			
	};
	
	//Controller
	ctx.CONTROLLER = {
			init:function(){
				this.prepared();
				this.bindEventListener();
				this.initclipboard();
			},
			prepared:function(needfillRefExample){
				var code_id= $(ctx.VIEW.Optionselected_Tasktype).data('ref');
				var isRequired = $(ctx.VIEW.Optionselected_Tasktype).data('required');
				isRequired = (isRequired!=undefined && (isRequired || isRequired=="true")) ;
				ctx.VIEW.components.refEditor.setRequired(isRequired);
				if(needfillRefExample && needfillRefExample==true){
					var sRef = $("#"+code_id).text();
					ctx.VIEW.components.refEditor.setContent(sRef);
				}
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Tasktype).change(function(){
					_this.prepared();
				});
				$(ctx.VIEW.Btn_Ref_example).click(function(){
					_this.prepared(true);
				});
				$(ctx.VIEW.Btn_Ref_clear).click(function(){
					ctx.VIEW.components.refEditor.setContent("");
				});
			},
			initclipboard:function(){
				var clipboard = new Clipboard('[data-role="copy"]');

				clipboard.on('success', function(e) {
				    console.log(e);
				});

				clipboard.on('error', function(e) {
				     console.log(e);
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);