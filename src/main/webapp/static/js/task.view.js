(function(ctx,$,win){
	//View
	ctx.VIEW = {
		"Link_Remove":"a[data-confirm='true']",
		"Dialog_Confirm":"#confirm-delete",
		"Input_Taskname":"#taskname",
		"Txtarea_Taskref":"#taskref",
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
					    lineWrapping: true,
					    readOnly: true
					  });
					editor.on("change",function(instance,changeObj){
				    	var sNew = instance.doc.getValue();
				    	_this.$txtarea.val(sNew);
				    });
					this.cm  = editor;
					//this.$txtarea.css({"visibility":"hidden","display":"inline","height":"0"});
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
				$(ctx.VIEW.Link_Remove).each(function(){
					var $link = $(this);
					$link.data('href',$link.attr('href'));
					$link.attr('href','#');
				});
				
				this.bindEventListener();
			},
			bindEventListener:function(){
				$(ctx.VIEW.Dialog_Confirm).on('show.bs.modal', function(e) {
					var $link = $(e.relatedTarget);
		            $(this).find('.btn-ok').attr('href', $link.data('href'));
		            var sTaskNmae = $(ctx.VIEW.Input_Taskname).val();
		            $(this).find('[data-title="taskname"]').text(sTaskNmae);
		        });
			}
			
	}//end of definition
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);