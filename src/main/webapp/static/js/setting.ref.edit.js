(function(ctx,$,win){
	//View
	ctx.VIEW = {
		"Txtarea_Protectedref":"#protectedref",	
		"Txtarea_Allref":"#allref",
		"Dialog_Info":"#allref-info",
		init:function(){
			this.components.init();
		}
	};
	ctx.VIEW.components = {
			init:function(){
				this.refEditor.init();
				this.infoEditor.init();
			},
			infoEditor:{
				init:function(){
					if(this.cm)
						return;
					this.$infotxtarea = $(ctx.VIEW.Txtarea_Allref);
					var infoeditor = CodeMirror.fromTextArea(this.$infotxtarea[0], {
					    lineWrapping: true,
					    lineNumbers: false,
					    readOnly:true,
					    autofocus:true
					  });
					this.cm  = infoeditor;
					
					
				}
			},
			refEditor : {
				init:function(){
					this.$txtarea = $(ctx.VIEW.Txtarea_Protectedref);
					
					
					var _this =this;
					var editor = CodeMirror.fromTextArea(this.$txtarea[0], {
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
				this.prepared();
				this.bindEventListener();
			},
			prepared:function(){//for ctx.VIEW.Select_Tasktype change callback
				//ctx.VIEW.components.refEditor.setContent(sRef);
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Dialog_Info).on('show.bs.modal', function(e) {
					ctx.VIEW.components.infoEditor.init();
		        });
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);