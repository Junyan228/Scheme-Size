package mindustry.ui.dialogs;

import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import arc.struct.*;
import mindustry.ui.*;
import mindustry.type.*; //mb remove
import mindustry.ctype.*;

import static mindustry.Vars.*;

public class ModContentSelectDialog<T extends UnlockableContent> extends BaseDialog{

	public Cons2<T, Floatp> callback;
	public StringS<Floatp> format;

	private Cell label;
	private Cell slider;

	public ModContentSelectDialog(String name, Seq<T> content, float min, float max, float step, StringS<Floatp> format){
		super(name);
		this.format = format;
		addCloseButton();

		var label = new Label("", Styles.outlineLabel);
		var slider = new Slider(min, max, step, false);
		slider.moved(value -> {
			label.setText(format.get(() -> value));
		});
		slider.change();

		var table = new Table();
		content.each(item -> {
			if (item.isHidden()) return;
			var drawable = new TextureRegionDrawable(item.icon(Cicon.full));
			table.button(drawable, () -> { 
				callback.get(item, () -> slider.getValue());
				hide(); 
			}).size(64);
			if (item.id % 10 == 9) table.row();
		});

		cont.add(table).row();
		this.label = cont.add(label);
		this.label.center().padTop(16).row();
		this.slider = cont.add(slider);
		this.slider.fillX().row();
	}

	public void select(boolean show, Cons2<T, Floatp> callback){
		this.callback = callback;
		label.visible(show);
		slider.visible(show);
		show();
	}

	public interface StringS<S>{
        String get(S s);
    }
}