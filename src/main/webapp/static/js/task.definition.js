(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Txtarea_Taskdef':'#taskdef',
			'Btn_Ref_example':'#taskref-toolbar button[data-role="example"]',
			'Btn_Ref_copy':'#taskref-toolbar button[data-role="copy"]',
			'Btn_Ref_clear':'#taskref-toolbar button[data-role="clear"]',
		init:function(){
			this.components.init();
		}
	};
	ctx.VIEW.components = {
			init:function(){
				this.defEditor.init();
			},
			defEditor : {
				init:function(){
					this.$txtarea = $(ctx.VIEW.Txtarea_Taskdef);
					
					var _this =this;
					var editor = CodeMirror.fromTextArea($(ctx.VIEW.Txtarea_Taskdef)[0], {
					    lineNumbers: true,
					    lineWrapping: true,
					    matchBrackets: true,
				        mode: "text/x-java"
					  });
					editor.on("change",function(instance,changeObj){
				    	var sNew = instance.doc.getValue();
				    	_this.$txtarea.val(sNew);
				    });
					this.cm  = editor;
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
				
			},
			bindEventListener:function(){
				
				$(ctx.VIEW.Btn_Ref_clear).click(function(){
					ctx.VIEW.components.defEditor.setContent("");
				});
			},
			initclipboard:function(){
				var clipboard = new Clipboard('[data-role="copy"]');

				clipboard.on('success', function(e) {
				    //console.log(e);
				});

				clipboard.on('error', function(e) {
				    //console.log(e);
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);