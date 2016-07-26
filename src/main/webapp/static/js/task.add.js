(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Select_Tasktype':'#tasktype',
			'Optionselected_Tasktype':'#tasktype option:selected',
			'Div_Tasktype_Ref':'#tasktype-ref',
			'Txtarea_Taskref':'#taskref',
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
					this.$txtarea.css({"visibility":"hidden","display":"inline","height":"0"});
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
			},
			prepared:function(){
				var code_id= $(ctx.VIEW.Optionselected_Tasktype).data('ref');
				var isRequired = $(ctx.VIEW.Optionselected_Tasktype).data('required');
				isRequired = (isRequired!=undefined && (isRequired || isRequired=="true")) ;
				ctx.VIEW.components.refEditor.setRequired(isRequired);
				var sRef = $("#"+code_id).text();
				ctx.VIEW.components.refEditor.setContent(sRef);
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Tasktype).change(function(){
					_this.prepared();
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);