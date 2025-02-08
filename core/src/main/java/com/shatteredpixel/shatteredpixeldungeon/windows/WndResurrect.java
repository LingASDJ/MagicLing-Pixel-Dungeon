/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.Statistics.TPDoorDieds;
import static com.shatteredpixel.shatteredpixeldungeon.Statistics.crivusfruitslevel2;
import static com.shatteredpixel.shatteredpixeldungeon.Statistics.crivusfruitslevel3;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.DragonGirlBlue;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;

public class WndResurrect extends Window {
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	private static final float BTN_GAP  = 5;

	private static final int BTN_SIZE	= 24;

	public static Object instance;

	private WndBlacksmith.ItemButtonX btnItem1;
	private WndBlacksmith.ItemButtonX btnItem2;
	private WndBlacksmith.ItemButtonX btnItem3;
	private WndBlacksmith.ItemButtonX btnItem4;
	private WndBlacksmith.ItemButtonX btnPressed;

	RedButton btnContinue;
	
	public WndResurrect( final Ankh ankh ) {
		
		super();
		
		instance = this;
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( ankh.image(), null ) );
		titlebar.label( Messages.titleCase(Messages.get(this, "title")) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		RenderedTextBlock message = PixelScene.renderTextBlock(Messages.get(this, "message"), 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

		btnItem1 = new WndBlacksmith.ItemButtonX() {
			@Override
			protected void onClick() {
				btnPressed = btnItem1;
				GameScene.selectItem( itemSelector );
			}
		};
		btnItem1.item(hero.belongings.weapon());
		btnItem1.setRect( (WIDTH - BTN_GAP) / 4 - BTN_SIZE, message.bottom() + BTN_GAP, BTN_SIZE, BTN_SIZE );
		add( btnItem1 );

		btnItem2 = new WndBlacksmith.ItemButtonX() {
			@Override
			protected void onClick() {
				btnPressed = btnItem2;
				GameScene.selectItem( itemSelector );
			}
		};
		btnItem2.item(hero.belongings.armor());
		btnItem2.setRect( btnItem1.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE );
		add( btnItem2 );

		Item item3 = null, item4 =null;
		if(hero.belongings.artifact() != null){
			item3 = hero.belongings.artifact();
		}else if(hero.belongings.misc() != null){
			item3 = hero.belongings.misc();
		}else {
			item3 = hero.belongings.ring();
		}
		if( hero.belongings.misc() != null && item3!=hero.belongings.misc() ){
			item4 = hero.belongings.misc();
		}else if(item3!=hero.belongings.ring()){
			item4 = hero.belongings.ring();
		}

		btnItem3 = new WndBlacksmith.ItemButtonX() {
			@Override
			protected void onClick() {
				btnPressed = btnItem3;
				GameScene.selectItem( itemSelector );
			}
		};
		btnItem3.item(item3);
		btnItem3.setRect( btnItem2.right() + BTN_GAP, btnItem2.top(), BTN_SIZE, BTN_SIZE );
		add( btnItem3 );


		btnItem4 = new WndBlacksmith.ItemButtonX() {
			@Override
			protected void onClick() {
				btnPressed = btnItem4;
				GameScene.selectItem( itemSelector );
			}
		};
		btnItem4.item(item4);
		btnItem4.setRect( btnItem3.right() + BTN_GAP, btnItem3.top(), BTN_SIZE, BTN_SIZE );
		add( btnItem4);
		
		btnContinue = new RedButton( Messages.get(this, "confirm") ) {
			@Override
			protected void onClick() {
				hide();
				
				Statistics.ankhsUsed++;

				ankh.detach(hero.belongings.backpack);

				if (btnItem1.item != null){
					btnItem1.item.keptThoughLostInvent = true;
				}
				if (btnItem2.item != null){
					btnItem2.item.keptThoughLostInvent = true;
				}
				if (btnItem3.item != null){
					btnItem3.item.keptThoughLostInvent = true;
				}
				if (btnItem4.item != null){
					btnItem4.item.keptThoughLostInvent = true;
				}

				if(Statistics.bossRushMode){
					//克里弗斯之果二阶段死亡的时候的给予重新评估
					if(crivusfruitslevel2){
						crivusfruitslevel2 = false;
					}
					if(crivusfruitslevel3){
						crivusfruitslevel3 = false;
					}
				} else {
					//克里弗斯之果二阶段死亡的时候的给予重新评估
					if(crivusfruitslevel2){
						crivusfruitslevel2 = false;
					}
				}


				//拟态王二阶段死亡的时候给予重新评估
				if(TPDoorDieds){
					TPDoorDieds = false;
				}

				DragonGirlBlue.Quest.four_used_points = 0;

				Statistics.sakaBackStage = 0;

				InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
				Game.switchScene( InterlevelScene.class );
			}
		};
		btnContinue.setRect( 0, btnItem1.bottom() + BTN_GAP, WIDTH, BTN_HEIGHT );
		add( btnContinue );

		resize( WIDTH, (int)btnContinue.bottom() );
	}

	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(WndResurrect.class, "prompt");
		}

		@Override
		public boolean itemSelectable(Item item) {
			// 不能选择某些物品（例如Ankh、Bag等）
			return !(item instanceof Ankh || item instanceof Bag);
		}

		@Override
		public void onSelect(Item item) {
			if (item != null && btnPressed.parent != null) {
				btnPressed.item(item);  // 设置当前按钮的物品

				// 定义按钮数组
				WndBlacksmith.ItemButtonX[] buttons = {btnItem1, btnItem2, btnItem3, btnItem4};

				// 遍历按钮数组，检查是否有相同物品
				for (int i = 0; i < buttons.length; i++) {
					for (int j = i + 1; j < buttons.length; j++) {
						if (buttons[i].item != null && buttons[i].item.equals(buttons[j].item)) {
							// 如果找到重复物品，清空另一个按钮的物品
							if (btnPressed == buttons[i]) {
								buttons[j].clear();
							} else {
								buttons[i].clear();
							}
						}
					}
				}
			}
		}
	};

	@Override
	public void destroy() {
		super.destroy();
		instance = null;
	}
	
	@Override
	public void onBackPressed() {
	}
}
