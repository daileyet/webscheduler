(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Txtarea_Taskdef':'#taskdef',
			'Select_tasktype':'#customtasktype',
			'Btn_Def_example':'#taskdef-toolbar button[data-role="example"]',
			'Btn_Def_copy':'#taskdef-toolbar button[data-role="copy"]',
			'Btn_Def_clear':'#taskdef-toolbar button[data-role="clear"]',
			'Btn_Def_full':'#taskdef-toolbar button[data-role="fullscreen"]',
			'Chk_Def_keepfile':'#keepfile',
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
				        mode: "text/x-java",
				        extraKeys: {
				            "F11": function(cm) {
				              cm.setOption("fullScreen", !cm.getOption("fullScreen"));
				            },
				            "Esc": function(cm) {
				              if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
				            }
				          }
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
				},
				fullscreen:function(){
					this.cm.setOption("fullScreen", !this.cm.getOption("fullScreen"));
				}
			}
	}
	
	//Model
	ctx.MODEL = {
			
	};
	
	//Controller
	ctx.CONTROLLER = {
			init:function(){
				//$(ctx.VIEW.Chk_Def_keepfile).bootstrapSwitch();
				this.prepared();
				this.bindEventListener();
				this.initclipboard();
				
			},
			prepared:function(needfillDefExample){
				
			},
			bindEventListener:function(){
				
				$(ctx.VIEW.Btn_Def_clear).click(function(){
					ctx.VIEW.components.defEditor.setContent("");
				});
				
				$(ctx.VIEW.Btn_Def_full).click(function(){
					ctx.VIEW.components.defEditor.fullscreen();
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