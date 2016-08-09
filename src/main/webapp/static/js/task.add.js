(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Select_Tasktype':'#tasktype',
			'Optionselected_Tasktype':'#tasktype option:selected',
			'Select_Triggertype':'#tasktrigger',
			'Optionselected_Triggertype':'#tasktrigger option:selected',
			'Div_Tasktype_Ref':'#tasktype-ref',
			'Txtarea_Taskref':'#taskref',
			'Input_Repeatable':'#repeatable',
			'Switch_bootstrap':'.bootstrap-switch',
			'Div_trigger_group':'.trigger-group',
			'Div_bind_element':'[data-bind-target]',
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
					//this.$txtarea.css({"visibility":"hidden","display":"inline","width":"0","margin-left":"30px","height":"0","padding":"0px","border-width":"0"});
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
				this.preparedTrigger();
				this.bindEventListener();
			},
			prepared:function(){//for ctx.VIEW.Select_Tasktype change callback
				var code_id= $(ctx.VIEW.Optionselected_Tasktype).data('ref');
				var isRequired = $(ctx.VIEW.Optionselected_Tasktype).data('required');
				isRequired = (isRequired!=undefined && (isRequired || isRequired=="true")) ;
				ctx.VIEW.components.refEditor.setRequired(isRequired);
				var sRef = $("#"+code_id).text();
				ctx.VIEW.components.refEditor.setContent(sRef);
			},
			preparedTrigger:function(){
				var sRefClass  = $(ctx.VIEW.Optionselected_Triggertype).data("ref")
				$(ctx.VIEW.Div_trigger_group).hide();
				$(ctx.VIEW.Div_bind_element).hide();
				$(sRefClass).fadeIn();
				$('[data-bind-target="'+sRefClass+'"]').fadeIn();
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Tasktype).change(function(){
					_this.prepared();
				});
				$(ctx.VIEW.Select_Triggertype).change(function(){
					_this.preparedTrigger();
				});
				
				$(ctx.VIEW.Switch_bootstrap).bootstrapSwitch();
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);