(function(ctx,$,win){
	//View
	ctx.VIEW = {
			'Txtarea_Taskdef':'#taskdef',
			'Select_tasktype':'#customtasktype',
			'Optionselected_tasktype':'#customtasktype option:selected',
			'txtarea_taskdef':'#taskdef',
			'txtarea_taskdefresult':'#taskdefresult',
			'tab_taskdefresult':'.nav-tabs a:last',
			
			'Btn_Def_compile':'#taskdef-toolbar button[data-role="compile"]',
			'Btn_Def_example':'#taskdef-toolbar button[data-role="example"]',
			'Btn_Def_copy':'#taskdef-toolbar button[data-role="copy"]',
			'Btn_Def_clear':'#taskdef-toolbar button[data-role="clear"]',
			'Btn_Def_full':'#taskdef-toolbar button[data-role="fullscreen"]',
			'Btn_Def_exit_full':'button[data-role="exit-fullscreen"]',
			'Chk_Def_keepfile':'#keepfile',
		init:function(){
			this.components.init();
		}
	};
	ctx.VIEW.components = {
			init:function(){
				this.defEditor.init();
				this.exitButton.init();
			},
			exitButton:{
				init:function(){
					var sHtml ='<button data-role="exit-fullscreen" type="button" class="btn btn-default" title="Exit fullscreen(press key: Esc)"> Exit </button>'
					var $exitBtn = $(sHtml);
					if($(ctx.VIEW.Btn_Def_exit_full).length==0){
						$exitBtn.appendTo($('.CodeMirror'));
					}
				}
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
			prepared:function(){
				
			},
			bindEventListener:function(){
				
				$(ctx.VIEW.Btn_Def_clear).click(function(){
					ctx.VIEW.components.defEditor.setContent("");
				});
				
				$(ctx.VIEW.Btn_Def_full).click(function(){
					ctx.VIEW.components.defEditor.fullscreen();
				});
				$(ctx.VIEW.Btn_Def_exit_full).click(function(){
					ctx.VIEW.components.defEditor.fullscreen();
				});
				
				$(ctx.VIEW.Btn_Def_compile).click(function(){
					var $btn = $(this);
					$.ajax({
						type: "post",
						url: $btn.data("link"),
						async: false,
						data: {
							"customtasktype": $(ctx.VIEW.Select_tasktype).val(),
							"taskdef":ctx.VIEW.components.defEditor.getContent()
						},
						dataType: "jsonp",
						success: function(data) {
							$(ctx.VIEW.txtarea_taskdefresult).val(data.msg);
							$(".compile-tag").remove();
							if (data.type == "SUCESS") {
								$(ctx.VIEW.tab_taskdefresult).append('<span class="compile-tag glyphicon glyphicon-ok-sign  pull-right text-success" style="z-index:999"></span>');
							} else {
								$(ctx.VIEW.tab_taskdefresult).append('<span class="compile-tag glyphicon glyphicon-remove-sign  pull-right text-danger" style="z-index:999"></span>');
								$(ctx.VIEW.tab_taskdefresult).tab('show');
							}
						},
						error: function() {
						}
					});
				});
				
				$(ctx.VIEW.Btn_Def_example).click(function(){
					var $btn = $(this);
					$.ajax({
						type: "get",
						url: $btn.data("link"),
						async: false,
						dataType: "xml",
						success: function(data) {
							$code = $(data).find("code");
							if($code.length>0){
								ctx.VIEW.components.defEditor.setContent($code.text());
							}
						},
						error: function() {
						}
					});
				});
				
				
				
				$(ctx.VIEW.Select_tasktype).change(function(){
					var $op = $(ctx.VIEW.Optionselected_tasktype);
					var sCustTaskType = $op.val();
					if(sCustTaskType==""){
						ctx.CONTROLLER.clear();
					}else{
						var sLink = $op.data("link");
						window.location = sLink;
					}
				});
			},
			clear:function(){
				ctx.VIEW.components.defEditor.setContent("");
				$(ctx.VIEW.txtarea_taskdefresult).val("");
			},
			initclipboard:function(){
				var clipboard = new Clipboard('[data-role="copy"]');
				clipboard.on('success', function(e) {
				});
				clipboard.on('error', function(e) {
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);