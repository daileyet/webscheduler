(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Select_Tasktype':'#tasktype',
			'Optionselected_Tasktype':'#tasktype option:selected',
			'Select_Triggertype':'#tasktrigger',
			'Optionselected_Triggertype':'#tasktrigger option:selected',
			'Select_Triggerepeatcount':'#repeatcount',
			'Optionselected_Triggerepeatcount':'#repeatcount option:selected',
			'Div_Tasktype_Ref':'#tasktype-ref',
			'Txtarea_Taskref':'#taskref',
			'Input_Repeatable':'#repeatable',
			'Input_Repeatinterval':'#repeatinterval',
			'Input_Startdate':'#startdate',
			'Input_Cronexpr':'#cronexpr',
			'Switch_bootstrap':'.bootstrap-switch',
			'Div_trigger_group':'.trigger-group',
			'Div_bind_element':'[data-bind-target]',
			'Div_repeat_options_group':'.repeat-options-group',
			'Div_form_datetime':'.form-datetime',
			'Addon_datetime':'.input-group-addon[role="datetime-addon"] ',
			'Addon_datetime_remove':'.input-group-addon[data-action="remove"]',
			'Addon_datetime_show':'.input-group-addon[data-action="show"]',
			
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
				$(ctx.VIEW.Switch_bootstrap).bootstrapSwitch();
				$(ctx.VIEW.Div_form_datetime).datetimepicker({
			        autoclose: true,
			        todayBtn: true,
			        todayHighlight: true,
			        startDate: new Date(),
			        minuteStep: 10
			    });
				this.prepared();
				this.preparedTrigger();
				this.preparedRepeatable();
				this.bindEventListener();
				this.initclipboard();
				//$(ctx.VIEW.Select_Triggerepeatcount).selectpicker('refresh');
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
			preparedTrigger:function(){
				var sRefClass  = $(ctx.VIEW.Optionselected_Triggertype).data("ref")
				$(ctx.VIEW.Div_trigger_group).hide();
				$(ctx.VIEW.Div_bind_element).hide();
				$(sRefClass).fadeIn();
				$('[data-bind-target="'+sRefClass+'"]').fadeIn();
				//add required property
				$(ctx.VIEW.Input_Startdate).prop("required",false);
				$(ctx.VIEW.Input_Cronexpr).prop("required",false);
				switch(sRefClass){
				case ".simple2-trigger":
					$(ctx.VIEW.Input_Startdate).prop("required",true);
					break;
				case ".cron-trigger":
					$(ctx.VIEW.Input_Cronexpr).prop("required",true);
					break;
				}
				
			},
			preparedRepeatable:function(state){
				var sRefClass = $(ctx.VIEW.Input_Repeatable).data("ref");
				$(sRefClass).hide();
				var _state = state;
				if(_state==undefined){
					_state = $(ctx.VIEW.Input_Repeatable).bootstrapSwitch('state');
				}
				$(ctx.VIEW.Input_Repeatable).val(_state);
				if(_state){
					$(sRefClass).fadeIn();
				}
				//add required property
				$(ctx.VIEW.Input_Repeatinterval).prop("required",_state);
				$(ctx.VIEW.Select_Triggerepeatcount).prop("required",_state);
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Tasktype).change(function(){
					_this.prepared();
				});
				$(ctx.VIEW.Select_Triggertype).change(function(){
					_this.preparedTrigger();
				});
				$(ctx.VIEW.Input_Repeatable).on('switchChange.bootstrapSwitch', function(event, state) {
					_this.preparedRepeatable(state);
				});
				$(ctx.VIEW.Addon_datetime_show).click(function(){
					var $addon = $(this);
					var sRole = $addon.data("action");
					$addon.siblings(ctx.VIEW.Div_form_datetime).datetimepicker(sRole);
				});
				
				$(ctx.VIEW.Addon_datetime_remove).click(function(){
					var $addon = $(this);
					$addon.siblings(ctx.VIEW.Div_form_datetime).val("");
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