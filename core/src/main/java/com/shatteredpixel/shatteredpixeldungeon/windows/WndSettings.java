/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

import static com.watabou.utils.DeviceCompat.isDesktop;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.Constants;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.NetIcons;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.services.news.News;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CrossDiedSprites;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.GameLog;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.OptionSlider;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ToobarV;
import com.shatteredpixel.shatteredpixeldungeon.ui.Toolbar;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class WndSettings extends WndTabbed {

	private static final int WIDTH_P	    = 122;
	private static final int WIDTH_L	    = 240;

	private static final int SLIDER_HEIGHT	= 24;
	private static final int BTN_HEIGHT	    = 18;
	private static final float GAP          = 2;

	private DisplayTab  display;
	private UITab       ui;
	private DataTab     data;
	private AudioTab    audio;
	private LangsTab    langs;
	private ExtendTab    extabs;
	private HelpTab    helps;
	private SeedfinderTab    seeds;

	public static int last_index = 0;

	public WndSettings() {
		super();

		float height;

		int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

		display = new DisplayTab();
		display.setSize(width, 0);
		height = display.height();
		add( display );

		add( new IconTab(Icons.get(Icons.DISPLAY)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				display.visible = display.active = value;
				if (value) last_index = 0;
			}
		});

		ui = new UITab();
		ui.setSize(width, 0);
		height = Math.max(height, ui.height());
		add( ui );

		add( new IconTab(Icons.get(Icons.PREFS)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				ui.visible = ui.active = value;
				if (value) last_index = 1;
			}
		});

		data = new DataTab();
		data.setSize(width, 0);
		height = Math.max(height, data.height());
		add( data );

		add( new IconTab(Icons.get(Icons.DATA)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				data.visible = data.active = value;
				if (value) last_index = 2;
			}
		});

		audio = new AudioTab();
		audio.setSize(width, 0);
		height = Math.max(height, audio.height());
		add( audio );

		add( new IconTab(Icons.get(Icons.AUDIO)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				audio.visible = audio.active = value;
				if (value) last_index = 3;
			}
		});

		langs = new LangsTab();
		langs.setSize(width, 0);
		height = Math.max(height, langs.height());
		add( langs );


		IconTab langsTab = new IconTab(Icons.get(Icons.LANGS)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				langs.visible = langs.active = value;
				if (value) last_index = 4;
			}

			@Override
			protected void createChildren() {
				super.createChildren();
				switch(Messages.lang().status()){
					case INCOMPLETE:
						icon.hardlight(1.5f, 0, 0);
						break;
					case UNREVIEWED:
						icon.hardlight(1.5f, 0.75f, 0f);
						break;
				}
			}

		};
		add( langsTab );

		extabs = new ExtendTab();
		extabs.setSize(width, 0);
		height = Math.max(height, audio.height());
		add( extabs );

		add( new IconTab(Icons.get(Icons.CHANGES)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				extabs.visible = extabs.active = value;
				if (value) last_index = 5;
			}
		});

		helps = new HelpTab();
		helps.setSize(width, 0);
		height = Math.max(height, audio.height());
		add( helps );

		add( new IconTab(new CrossDiedSprites()){
			@Override
			protected void select(boolean value) {
				super.select(value);
				helps.visible = helps.active = value;
				if (value) last_index = 6;
			}
		});

		seeds = new SeedfinderTab();
		seeds.setSize(width, 0);
		height = Math.max(height, audio.height());
		add( seeds );

		add( new IconTab(NetIcons.get(NetIcons.GLOBE)){
			@Override
			protected void select(boolean value) {
				super.select(value);
				seeds.visible = seeds.active = value;
				if (value) last_index = 7;
			}
		});

		resize(width, (int)Math.ceil(height));

		layoutTabs();

		select(last_index);

	}

	@Override
	public void hide() {
		super.hide();
		//resets generators because there's no need to retain chars for languages not selected
		ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
			@Override
			public void beforeCreate() {
				Game.platform.resetGenerators();
			}
			@Override
			public void afterCreate() {
				//do nothing
			}
		});
	}

	private static class DisplayTab extends Component {

		RenderedTextBlock title;
		ColorBlock sep1;
		CheckBox chkFullscreen;
		OptionSlider optScale;
		CheckBox chkSaver;
		RedButton btnOrientation;
		ColorBlock sep2;
		OptionSlider optBrightness;
		OptionSlider optVisGrid;
		OptionSlider optFollowIntensity;
		OptionSlider optScreenShake;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			chkFullscreen = new CheckBox( Messages.get(this, "fullscreen") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.fullscreen(checked());
				}
			};
			if (DeviceCompat.supportsFullScreen()){
				chkFullscreen.checked(SPDSettings.fullscreen());
			} else {
				chkFullscreen.checked(true);
				chkFullscreen.enable(false);
			}
			add(chkFullscreen);

			if (DeviceCompat.isAndroid() && PixelScene.maxScreenZoom >= 2) {
				chkSaver = new CheckBox(Messages.get(this, "saver")) {
					@Override
					protected void onClick() {
						super.onClick();
						if (checked()) {
							checked(!checked());
							ShatteredPixelDungeon.scene().add(new WndOptions(Icons.get(Icons.DISPLAY),
									Messages.get(DisplayTab.class, "saver"),
									Messages.get(DisplayTab.class, "saver_desc"),
									Messages.get(DisplayTab.class, "okay"),
									Messages.get(DisplayTab.class, "cancel")) {
								@Override
								protected void onSelect(int index) {
									if (index == 0) {
										checked(!checked());
										SPDSettings.powerSaver(checked());
									}
								}
							});
						} else {
							SPDSettings.powerSaver(checked());
						}
					}
				};
				chkSaver.checked( SPDSettings.powerSaver() );
				add( chkSaver );
			}

			if (DeviceCompat.isAndroid()) {
				Boolean landscape = SPDSettings.landscape();
				if (landscape == null){
					landscape = Game.width > Game.height;
				}
				Boolean finalLandscape = landscape;
				btnOrientation = new RedButton(finalLandscape ?
						Messages.get(this, "portrait")
						: Messages.get(this, "landscape")) {
					@Override
					protected void onClick() {
						SPDSettings.landscape(!finalLandscape);
					}
				};
				add(btnOrientation);
			}

			sep2 = new ColorBlock(1, 1, 0xFF000000);
			add(sep2);

			optBrightness = new OptionSlider(Messages.get(this, "brightness"),
					Messages.get(this, "dark"), Messages.get(this, "bright"), -1, 1) {
				@Override
				protected void onChange() {
					SPDSettings.brightness(getSelectedValue());
				}
			};
			optBrightness.setSelectedValue(SPDSettings.brightness());
			add(optBrightness);

			optVisGrid = new OptionSlider(Messages.get(this, "visual_grid"),
					Messages.get(this, "off"), Messages.get(this, "high"), -1, 2) {
				@Override
				protected void onChange() {
					SPDSettings.visualGrid(getSelectedValue());
				}
			};
			optVisGrid.setSelectedValue(SPDSettings.visualGrid());
			add(optVisGrid);

			optFollowIntensity = new OptionSlider(Messages.get(this, "camera_follow"),
					Messages.get(this, "low"), Messages.get(this, "high"), 1, 4) {
				@Override
				protected void onChange() {
					SPDSettings.cameraFollow(getSelectedValue());
				}
			};
			optFollowIntensity.setSelectedValue(SPDSettings.cameraFollow());
			add(optFollowIntensity);

			optScreenShake = new OptionSlider(Messages.get(this, "screenshake"),
					Messages.get(this, "off"), Messages.get(this, "high"), 0, 4) {
				@Override
				protected void onChange() {
					SPDSettings.screenShake(getSelectedValue());
				}
			};
			optScreenShake.setSelectedValue(SPDSettings.screenShake());
			add(optScreenShake);

		}

		@Override
		protected void layout() {

			float bottom = y;

			title.setPos((width - title.width())/2, bottom + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 3*GAP;

			bottom = sep1.y + 1;

			if (width > 200 && chkSaver != null) {
				chkFullscreen.setRect(0, bottom + GAP, width/2-1, BTN_HEIGHT);
				chkSaver.setRect(chkFullscreen.right()+ GAP, bottom + GAP, width/2-1, BTN_HEIGHT);
				bottom = chkFullscreen.bottom();
			} else {
				chkFullscreen.setRect(0, bottom + GAP, width, BTN_HEIGHT);
				bottom = chkFullscreen.bottom();

				if (chkSaver != null) {
					chkSaver.setRect(0, bottom + GAP, width, BTN_HEIGHT);
					bottom = chkSaver.bottom();
				}
			}

			if (btnOrientation != null) {
				btnOrientation.setRect(0, bottom + GAP, width, BTN_HEIGHT);
				bottom = btnOrientation.bottom();
			}

			if (optScale != null){
				optScale.setRect(0, bottom + GAP, width, SLIDER_HEIGHT);
				bottom = optScale.bottom();
			}

			sep2.size(width, 1);
			sep2.y = bottom + GAP;
			bottom = sep2.y + 1;

			if (width > 200){
				optBrightness.setRect(0, bottom + GAP, width/2-GAP/2, SLIDER_HEIGHT);
				optVisGrid.setRect(optBrightness.right() + GAP, optBrightness.top(), width/2-GAP/2, SLIDER_HEIGHT);

				optFollowIntensity.setRect(0, optVisGrid.bottom() + GAP, width/2-GAP/2, SLIDER_HEIGHT);
				optScreenShake.setRect(optFollowIntensity.right() + GAP, optFollowIntensity.top(), width/2-GAP/2, SLIDER_HEIGHT);
			} else {
				optBrightness.setRect(0, bottom + GAP, width, SLIDER_HEIGHT);
				optVisGrid.setRect(0, optBrightness.bottom() + GAP, width, SLIDER_HEIGHT);

				optFollowIntensity.setRect(0, optVisGrid.bottom() + GAP, width, SLIDER_HEIGHT);
				optScreenShake.setRect(0, optFollowIntensity.bottom() + GAP, width, SLIDER_HEIGHT);
			}

			height = optScreenShake.bottom();
		}

	}

	private static class UITab extends Component {

		RenderedTextBlock title;

		ColorBlock sep1;
		OptionSlider optUIMode;
		RedButton btnKeyBindings;
		OptionSlider optUIScale;
		RedButton btnToolbarSettings;
		CheckBox chkFlipTags;
		ColorBlock sep2;
		CheckBox chkFont;

		OptionSlider quickslots;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			//add slider for UI size only if device has enough space to support it
			float wMin = Game.width / PixelScene.MIN_WIDTH_FULL;
			float hMin = Game.height / PixelScene.MIN_HEIGHT_FULL;
			if (Math.min(wMin, hMin) >= 2*Game.density && !SPDSettings.quickSwapper()){
				optUIMode = new OptionSlider(
						Messages.get(this, "ui_mode"),
						Messages.get(this, "mobile"),
						Messages.get(this, "full"),
						0,
						1
				) {
					@Override
					protected void onChange() {
						SPDSettings.interfaceSize(getSelectedValue());
						ShatteredPixelDungeon.seamlessResetScene();
					}
				};
				optUIMode.setSelectedValue(SPDSettings.interfaceSize());
				add(optUIMode);
			}

			if ((int)Math.ceil(2* Game.density) < PixelScene.maxDefaultZoom) {
				optUIScale = new OptionSlider(Messages.get(this, "scale"),
						(int)Math.ceil(2* Game.density)+ "X",
						PixelScene.maxDefaultZoom + "X",
						(int)Math.ceil(2* Game.density),
						PixelScene.maxDefaultZoom ) {
					@Override
					protected void onChange() {
						if (getSelectedValue() != SPDSettings.scale()) {
							SPDSettings.scale(getSelectedValue());
							ShatteredPixelDungeon.seamlessResetScene();
						}
					}
				};
				optUIScale.setSelectedValue(PixelScene.defaultZoom);
				add(optUIScale);
			}

			if (SPDSettings.interfaceSize() == 0) {
				btnToolbarSettings = new RedButton(Messages.get(this, "toolbar_settings"), 9){
					@Override
					protected void onClick() {
						ShatteredPixelDungeon.scene().addToFront(new Window(){

							RenderedTextBlock barDesc;
							RedButton btnSplit; RedButton btnGrouped; RedButton btnCentered;
							CheckBox chkQuickSwapper;
							RenderedTextBlock swapperDesc;
							CheckBox chkFlipToolbar;
							CheckBox chkFlipTags;

							{
								barDesc = PixelScene.renderTextBlock(Messages.get(WndSettings.UITab.this, "mode"), 9);
								add(barDesc);

								btnSplit = new RedButton(Messages.get(WndSettings.UITab.this, "split")) {
									@Override
									protected void onClick() {
										textColor(TITLE_COLOR);
										btnGrouped.textColor(WHITE);
										btnCentered.textColor(WHITE);
										SPDSettings.toolbarMode(ToobarV.Mode.SPLIT.name());
										ToobarV.updateLayout();
									}
								};
								if (SPDSettings.toolbarMode().equals(ToobarV.Mode.SPLIT.name())) {
									btnSplit.textColor(TITLE_COLOR);
								}
								add(btnSplit);

								btnGrouped = new RedButton(Messages.get(WndSettings.UITab.this, "group")) {
									@Override
									protected void onClick() {
										btnSplit.textColor(WHITE);
										textColor(TITLE_COLOR);
										btnCentered.textColor(WHITE);
										SPDSettings.toolbarMode(ToobarV.Mode.GROUP.name());
										ToobarV.updateLayout();
									}
								};
								if (SPDSettings.toolbarMode().equals(ToobarV.Mode.GROUP.name())) {
									btnGrouped.textColor(TITLE_COLOR);
								}
								add(btnGrouped);

								btnCentered = new RedButton(Messages.get(WndSettings.UITab.this, "center")) {
									@Override
									protected void onClick() {
										btnSplit.textColor(WHITE);
										btnGrouped.textColor(WHITE);
										textColor(TITLE_COLOR);
										SPDSettings.toolbarMode(ToobarV.Mode.CENTER.name());
										ToobarV.updateLayout();
									}
								};
								if (SPDSettings.toolbarMode().equals(ToobarV.Mode.CENTER.name())) {
									btnCentered.textColor(TITLE_COLOR);
								}
								add(btnCentered);

								if(SPDSettings.quickSwapper()){
									btnSplit.alpha(0.5f);
									btnGrouped.alpha(0.5f);
									btnCentered.alpha(0.5f);
									btnCentered.active = false;
									btnGrouped.active = false;
									btnSplit.active = false;
								}

								chkQuickSwapper = new CheckBox(Messages.get(WndSettings.UITab.this, "quickslot_swapper")) {
									@Override
									protected void onClick() {
										super.onClick();
										SPDSettings.quickSwapper(checked());
										ShatteredPixelDungeon.resetScene();
									}
								};
								chkQuickSwapper.checked(SPDSettings.quickSwapper());
								chkQuickSwapper.enable(!SPDSettings.intro());
								add(chkQuickSwapper);

								swapperDesc = PixelScene.renderTextBlock(Messages.get(WndSettings.UITab.this, "swapper_desc"), 5);
								swapperDesc.hardlight(0x888888);
								add(swapperDesc);

								chkFlipToolbar = new CheckBox(Messages.get(WndSettings.UITab.this, "flip_toolbar")) {
									@Override
									protected void onClick() {
										super.onClick();
										SPDSettings.flipToolbar(checked());
										//ToobarV.updateLayout();
									}
								};
								chkFlipToolbar.checked(SPDSettings.flipToolbar());
								add(chkFlipToolbar);

								chkFlipTags = new CheckBox(Messages.get(WndSettings.UITab.this, "flip_indicators")){
									@Override
									protected void onClick() {
										super.onClick();
										SPDSettings.flipTags(checked());
										GameScene.layoutTags();
									}
								};
								chkFlipTags.checked(SPDSettings.flipTags());
								add(chkFlipTags);

								//layout
								resize(WIDTH_P, 0);

								barDesc.setPos((width - barDesc.width()) / 2f, GAP);
								PixelScene.align(barDesc);

								int btnWidth = (int) (width - 2 * GAP) / 3;

									btnSplit.setRect(0, barDesc.bottom() + GAP, btnWidth, BTN_HEIGHT-2);
									btnGrouped.setRect(btnSplit.right() + GAP, btnSplit.top(), btnWidth, BTN_HEIGHT-2);
									btnCentered.setRect(btnGrouped.right() + GAP, btnSplit.top(), btnWidth, BTN_HEIGHT-2);
									chkQuickSwapper.setRect(0, btnGrouped.bottom() + GAP, width, BTN_HEIGHT);


								swapperDesc.maxWidth(width);
								swapperDesc.setPos(0, chkQuickSwapper.bottom()+1);

								if (width > 200) {
									chkFlipToolbar.setRect(0, swapperDesc.bottom() + GAP, width / 2 - 1, BTN_HEIGHT);
									chkFlipTags.setRect(chkFlipToolbar.right() + GAP, chkFlipToolbar.top(), width / 2 - 1, BTN_HEIGHT);
								} else {
									chkFlipToolbar.setRect(0, swapperDesc.bottom() + GAP, width, BTN_HEIGHT);
									chkFlipTags.setRect(0, chkFlipToolbar.bottom() + GAP, width, BTN_HEIGHT);
								}

								resize(WIDTH_P, (int)chkFlipTags.bottom());

							}
						});
					}
				};
				add(btnToolbarSettings);

			} else {

				chkFlipTags = new CheckBox(Messages.get(this, "flip_indicators")) {
					@Override
					protected void onClick() {
						super.onClick();
						SPDSettings.flipTags(checked());
						GameScene.layoutTags();
					}
				};
				chkFlipTags.checked(SPDSettings.flipTags());
				add(chkFlipTags);

			}

			sep2 = new ColorBlock(1, 1, 0xFF000000);
			add(sep2);

			chkFont = new CheckBox(Messages.get(this, "system_font")){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
						@Override
						public void beforeCreate() {
							SPDSettings.systemFont(checked());
						}

						@Override
						public void afterCreate() {
							//do nothing
						}
					});
				}
			};
			chkFont.checked(SPDSettings.systemFont());
			add(chkFont);

			quickslots = new OptionSlider(Messages.get(this, "quickslots"), "" + Constants.MIN_QUICKSLOTS,
					"" + Constants.MAX_QUICKSLOTS, Constants.MIN_QUICKSLOTS, Constants.MAX_QUICKSLOTS) {
				@Override
				protected void onChange() {
					SPDSettings.quickslots(getSelectedValue());
					if(SPDSettings.quickSwapper()){
						Toolbar.updateLayout();
					} else {
						ToobarV.updateLayout();
					}

				}
			};
			quickslots.setSelectedValue(SPDSettings.quickslots());
			add(quickslots);

			btnKeyBindings = new RedButton(Messages.get(this, "key_bindings")){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().addToFront(new WndKeyBindings(false));
				}
			};

			add(btnKeyBindings);

		}

		@Override
		protected void layout() {
			title.setPos((width - title.width())/2, y + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 3*GAP;



			height = sep1.y + 1;

			if (optUIMode != null && optUIScale != null && width > 200){
				optUIMode.setRect(0, height + GAP, width/2-1, SLIDER_HEIGHT);
				optUIScale.setRect(width/2+1, height + GAP, width/2-1, SLIDER_HEIGHT);
				height = optUIScale.bottom();
			} else {
				if (optUIMode != null) {
					optUIMode.setRect(0, height + GAP, width, SLIDER_HEIGHT);
					height = optUIMode.bottom();
				}

				if (optUIScale != null) {
					optUIScale.setRect(0, height + GAP, width, SLIDER_HEIGHT);
					height = optUIScale.bottom();
				}
			}



			if (btnToolbarSettings != null) {
				btnToolbarSettings.setRect(0, height + GAP, width, BTN_HEIGHT);
				height = btnToolbarSettings.bottom();
			} else {
				chkFlipTags.setRect(0, height + GAP, width, BTN_HEIGHT);
				height = chkFlipTags.bottom();
			}

			sep2.size(width, 1);
			sep2.y = height + GAP;


			if(!SPDSettings.quickSwapper()){
				quickslots.active = false;
				quickslots.visible = false;
			}

			if ((Game.scene() == null || Game.scene().getClass() != GameScene.class) && SPDSettings.quickSwapper()) {
				chkFont.setRect(0, sep2.y + 1 + GAP, width, BTN_HEIGHT);
				quickslots.visible = false;
				quickslots.active = false;
			} else {
				chkFont.setRect(0, sep2.y + 1 + GAP, width/2, BTN_HEIGHT);
				quickslots.setRect(width/2, sep2.y + 1 + GAP, width/2, BTN_HEIGHT);
			}



			height = chkFont.bottom();

			if (!isDesktop()) {
				btnKeyBindings.active = false;
				btnKeyBindings.alpha(0.3f);
			}
			btnKeyBindings.setRect(0, height + GAP, width, BTN_HEIGHT);
			height = btnKeyBindings.bottom();

		}

	}

	private static class ExtendTab extends Component {

		RenderedTextBlock title;

		ColorBlock sep1;
		CheckBox ClassUI;
		OptionSlider optSplashScreen;

		CheckBox optFPSLimit;

		CheckBox optIcon;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			ClassUI = new CheckBox( Messages.get(this, "dark_ui") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.ClassUI(checked());
				}
			};
			ClassUI.checked(SPDSettings.ClassUI());
			add(ClassUI);

			optSplashScreen = new OptionSlider(Messages.get(this, "splash_screen"),
					Messages.get(this, "disable" ),
					Messages.get( this, "full" ),
					0, 1) {
				@Override
				protected void onChange() {
					if (getSelectedValue() != SPDSettings.splashScreen()) {
						SPDSettings.splashScreen(getSelectedValue());
					}
				}
			};
			optSplashScreen.setSelectedValue(SPDSettings.splashScreen());
			add(optSplashScreen);

			optFPSLimit = new CheckBox( Messages.get(this, "fpsdisplay") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.TimeLimit(checked());
				}
			};
			optFPSLimit.checked(SPDSettings.TimeLimit());
			add(optFPSLimit);

			optIcon = new CheckBox( Messages.get(this, "iconv2") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.V2IconDamage(checked());
				}
			};
			optIcon.checked(SPDSettings.V2IconDamage());
			add(optIcon);
		}

		@Override
		protected void layout() {

			float bottom = y;

			title.setPos((width - title.width())/2, bottom + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 2*GAP;

			bottom = sep1.y + 1;

			if (width > 200){
				ClassUI.setRect(0, bottom, width, SLIDER_HEIGHT);
				optSplashScreen.setRect(0, ClassUI.bottom() + GAP, width, SLIDER_HEIGHT);
				optFPSLimit.setRect(0, optSplashScreen.bottom() + GAP, width/2, SLIDER_HEIGHT);
				optIcon.setRect(optFPSLimit.right(), optSplashScreen.bottom() + GAP, width/2, SLIDER_HEIGHT);
			} else {
					ClassUI.setRect(0, bottom + GAP, width, SLIDER_HEIGHT);
					optSplashScreen.setRect(0, ClassUI.bottom() + GAP, width, SLIDER_HEIGHT);
					optFPSLimit.setRect(0, optSplashScreen.bottom() + GAP, width, SLIDER_HEIGHT);
					optIcon.setRect(0, optFPSLimit.bottom() + GAP, width, SLIDER_HEIGHT);
			}

			height = optIcon.bottom();
		}

	}

	private static class SeedfinderTab extends Component {
		RenderedTextBlock title;
		ColorBlock sep1;
		OptionSlider numFloors;
		RedButton btnChallenges;
		RedButton btnMode;
		CheckBox PlusSearch;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			boolean isDesktop = DeviceCompat.isDesktop();

			numFloors = new OptionSlider(Messages.get(this, "floors_slider") + " (" + SPDSettings.seedfinderFloors() + ")",
					"1", "30", 1,  30) {
				@Override
				protected void onChange() {
					SPDSettings.seedfinderFloors(getSelectedValue());

					// reload scene for floor number desc
					ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
						@Override
						public void beforeCreate() {
						}

						@Override
						public void afterCreate() {
							//do nothing
						}
					});
				}
			};
			numFloors.setSelectedValue(SPDSettings.seedfinderFloors());
			add(numFloors);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			btnChallenges = new RedButton(Messages.get(WndSettings.SeedfinderTab.this, "challenges")){
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.scene().addToFront(new WndChallenges(SPDSettings.challenges(), true,null) {
						public void onBackPressed() {
							super.onBackPressed();

							// reload scene for new button color
							ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
								@Override
								public void beforeCreate() {
								}

								@Override
								public void afterCreate() {
									//do nothing
								}
							});
						}
					});
				}
			};
			btnChallenges.textColor(SPDSettings.challenges() == 0 ? WHITE : TITLE_COLOR);
			add(btnChallenges);

			String modeBtnDescKey = SPDSettings.seedfinderConditionANY() ? "mode_any" : "mode_all";
			btnMode = new RedButton(Messages.get(WndSettings.SeedfinderTab.this, modeBtnDescKey)){
				@Override
				protected void onClick() {
					SPDSettings.seedfinderConditionANY(!SPDSettings.seedfinderConditionANY());

					// reload scene for new button text
					ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
						@Override
						public void beforeCreate() {
						}

						@Override
						public void afterCreate() {
							//do nothing
						}
					});
				}
			};
			add(btnMode);

			PlusSearch = new CheckBox( Messages.get(this, "plus_search") ) {
				@Override
				protected void onClick() {
					super.onClick();
					if (checked()) {
						checked(!checked());
						ShatteredPixelDungeon.scene().add(new WndOptions(new Image(new ItemSprite(ItemSpriteSheet.SEED_AIKELAIER)),
								Messages.get(SeedfinderTab.class, "plus"),
								Messages.get(SeedfinderTab.class, "plus_desc"),
								Messages.get(DisplayTab.class, "okay"),
								Messages.get(DisplayTab.class, "cancel")) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) {
									checked(!checked());
									SPDSettings.PlusSearch(checked());
								}
							}
						});
					} else {
						SPDSettings.PlusSearch(checked());
					}
				}
			};
			PlusSearch.checked(SPDSettings.PlusSearch());
			add(PlusSearch);
		}

		@Override
		protected void layout() {

			float bottom = y;

			title.setPos((width - title.width())/2, bottom + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 3*GAP;

			bottom = sep1.y + 1;

			numFloors.setRect(0, bottom + GAP, width, SLIDER_HEIGHT);

			btnChallenges.setRect(0, numFloors.bottom() + GAP, width / 2 - 1, BTN_HEIGHT);
			btnMode.setRect(width/2 + 1, numFloors.bottom() + GAP, width / 2, BTN_HEIGHT);
			PlusSearch.setRect(0, btnChallenges.bottom() + GAP, width, BTN_HEIGHT);
		}

	}

	private static class HelpTab extends Component {

		RenderedTextBlock title;
		ColorBlock sep1;
		CheckBox LockFing;

		CheckBox ATBSwitch;

		CheckBox VSBwitch;

		CheckBox RTC_itch;

		//OptionSlider timeOut;

//		RedButton ResetButton;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			LockFing = new CheckBox( Messages.get(this, "helpsettings") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.HelpSettings(checked());
				}
			};
			LockFing.checked(SPDSettings.HelpSettings());
			add(LockFing);

			ATBSwitch = new CheckBox( Messages.get(this, "atbsettings") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.ATBSettings(checked());
				}
			};
			ATBSwitch.checked(SPDSettings.ATBSettings());
			add(ATBSwitch);

			VSBwitch = new CheckBox( Messages.get(this, "vsbsettings") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.visualBuddle(checked());
				}
			};
			VSBwitch.checked(SPDSettings.visualBuddle());
			add(VSBwitch);

			RTC_itch = new CheckBox( Messages.get(this, "rtb_settings") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.UPos(checked());
				}
			};
			RTC_itch.checked(SPDSettings.UPos());
			add(RTC_itch);
		}

		@Override
		protected void layout() {

			float bottom = y;

			title.setPos((width - title.width())/2, bottom + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 2*GAP;

			bottom = sep1.y + 1;

			if (width > 200){
				LockFing.setRect(0, bottom, width, SLIDER_HEIGHT);
				ATBSwitch.setRect(0, LockFing.bottom() + GAP, width/2, SLIDER_HEIGHT);
				VSBwitch.setRect(width/2, LockFing.bottom() + GAP, width/2, SLIDER_HEIGHT);
				RTC_itch.setRect(0, VSBwitch.bottom() + GAP, width, SLIDER_HEIGHT);
            } else {
				LockFing.setRect(0, bottom + GAP, width, SLIDER_HEIGHT);
				ATBSwitch.setRect(0, LockFing.bottom() + GAP, width, SLIDER_HEIGHT);
				VSBwitch.setRect(0, ATBSwitch.bottom() + GAP, width, SLIDER_HEIGHT);
				RTC_itch.setRect(0, VSBwitch.bottom() + GAP, width, SLIDER_HEIGHT);
            }


            height = RTC_itch.bottom();
		}

	}

	private static class DataTab extends Component{

		RenderedTextBlock title;
		ColorBlock sep1;
		CheckBox chkNews;
		CheckBox chkBetas;
		CheckBox chkWifi;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			chkNews = new CheckBox(Messages.get(this, "news")){
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.news(checked());
					News.clearArticles();
				}
			};
			chkNews.checked(SPDSettings.news());
			add(chkNews);

			if (!DeviceCompat.isDesktop()){
				chkWifi = new CheckBox(Messages.get(this, "wifi")){
					@Override
					protected void onClick() {
						super.onClick();
						SPDSettings.WiFi(checked());
					}
				};
				chkWifi.checked(SPDSettings.WiFi());
				add(chkWifi);
			}
		}

		@Override
		protected void layout() {
			title.setPos((width - title.width())/2, y + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 2*GAP;

			float pos;

			if (width > 200){
				chkNews.setRect(0, sep1.y + 1 + GAP, width/2-1, BTN_HEIGHT);
			} else {
				chkNews.setRect(0, sep1.y + 1 + GAP, width, BTN_HEIGHT);
			}

			pos = chkNews.bottom();
			if (chkBetas != null){
				chkBetas.setRect(0, pos + GAP, width, BTN_HEIGHT);
				pos = chkBetas.bottom();
			}

			if (chkWifi != null){
				chkWifi.setRect(0, pos + GAP, width, BTN_HEIGHT);
				pos = chkWifi.bottom();
			}

			height = pos;

		}
	}

	private static class AudioTab extends Component {

		RenderedTextBlock title;
		ColorBlock sep1;
		OptionSlider optMusic;
		CheckBox chkMusicMute;
		ColorBlock sep2;
		OptionSlider optSFX;
		CheckBox chkMuteSFX;
		ColorBlock sep3;
		CheckBox chkIgnoreSilent;


		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			optMusic = new OptionSlider(Messages.get(this, "music_vol"), "0", "10", 0, 10) {
				@Override
				protected void onChange() {
					SPDSettings.musicVol(getSelectedValue());
				}
			};
			optMusic.setSelectedValue(SPDSettings.musicVol());
			add(optMusic);

			chkMusicMute = new CheckBox(Messages.get(this, "music_mute")){
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.music(!checked());
				}
			};
			chkMusicMute.checked(!SPDSettings.music());
			add(chkMusicMute);

			sep2 = new ColorBlock(1, 1, 0xFF000000);
			add(sep2);

			optSFX = new OptionSlider(Messages.get(this, "sfx_vol"), "0", "10", 0, 10) {
				@Override
				protected void onChange() {
					SPDSettings.SFXVol(getSelectedValue());
					if (Random.Int(100) == 0){
						Sample.INSTANCE.play(Assets.Sounds.MIMIC);
					} else {
						Sample.INSTANCE.play(Random.oneOf(Assets.Sounds.GOLD,
								Assets.Sounds.HIT,
								Assets.Sounds.ITEM,
								Assets.Sounds.SHATTER,
								Assets.Sounds.EVOKE,
								Assets.Sounds.SECRET));
					}
				}
			};
			optSFX.setSelectedValue(SPDSettings.SFXVol());
			add(optSFX);

			chkMuteSFX = new CheckBox( Messages.get(this, "sfx_mute") ) {
				@Override
				protected void onClick() {
					super.onClick();
					SPDSettings.soundFx(!checked());
					Sample.INSTANCE.play( Assets.Sounds.CLICK );
				}
			};
			chkMuteSFX.checked(!SPDSettings.soundFx());
			add( chkMuteSFX );

			if (DeviceCompat.isiOS() && Messages.lang() == Languages.ENGLISH){

				sep3 = new ColorBlock(1, 1, 0xFF000000);
				add(sep3);

				chkIgnoreSilent = new CheckBox( Messages.get(this, "ignore_silent") ){
					@Override
					protected void onClick() {
						super.onClick();
						SPDSettings.ignoreSilentMode(checked());
					}
				};
				chkIgnoreSilent.checked(SPDSettings.ignoreSilentMode());
				add(chkIgnoreSilent);
			}
		}

		@Override
		protected void layout() {
			title.setPos((width - title.width())/2, y + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 2*GAP;

			if (width > 200) {
				optMusic.setRect(0, sep1.y + 1 + GAP, width/2-1, SLIDER_HEIGHT);
				chkMusicMute.setRect(0, optMusic.bottom() + GAP, width/2-1, BTN_HEIGHT);

				sep2.size(width, 1);
				sep2.y = sep1.y; //just have them overlap

				optSFX.setRect(optMusic.right()+2, sep2.y + 1 + GAP, width/2-1, SLIDER_HEIGHT);
				chkMuteSFX.setRect(chkMusicMute.right()+2, optSFX.bottom() + GAP, width/2-1, BTN_HEIGHT);

			} else {
				optMusic.setRect(0, sep1.y + 1 + GAP, width, SLIDER_HEIGHT);
				chkMusicMute.setRect(0, optMusic.bottom() + GAP, width, BTN_HEIGHT);

				sep2.size(width, 1);
				sep2.y = chkMusicMute.bottom() + GAP;

				optSFX.setRect(0, sep2.y + 1 + GAP, width, SLIDER_HEIGHT);
				chkMuteSFX.setRect(0, optSFX.bottom() + GAP, width, BTN_HEIGHT);
			}

			height = chkMuteSFX.bottom();

			if (chkIgnoreSilent != null){
				sep3.size(width, 1);
				sep3.y = chkMuteSFX.bottom() + GAP;

				chkIgnoreSilent.setRect(0, sep3.y + 1 + GAP, width, BTN_HEIGHT);
				height = chkIgnoreSilent.bottom();
			}
		}

	}

	private static class LangsTab extends Component{

		final static int COLS_P = 1;
		final static int COLS_L = 2;

		RenderedTextBlock title;
		ColorBlock sep1;
		RenderedTextBlock txtLangName;
		RenderedTextBlock txtLangInfo;
		ColorBlock sep2;
		RedButton[] lanBtns;
		ColorBlock sep3;
		RenderedTextBlock txtTranifex;
		RedButton btnCredits;

		@Override
		protected void createChildren() {
			title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			sep1 = new ColorBlock(1, 1, 0xFF000000);
			add(sep1);

			final ArrayList<Languages> langs = new ArrayList<>(Arrays.asList(Languages.values()));

			Languages nativeLang = Languages.matchLocale(Locale.getDefault());
			langs.remove(nativeLang);
			//move the native language to the top.
			langs.add(0, nativeLang);

			final Languages currLang = Messages.lang();

			txtLangName = PixelScene.renderTextBlock( Messages.titleCase(currLang.nativeName()) , 9 );
			if (currLang.status() == Languages.Status.REVIEWED) txtLangName.hardlight(TITLE_COLOR);
			else if (currLang.status() == Languages.Status.UNREVIEWED) txtLangName.hardlight(CharSprite.WARNING);
			else if (currLang.status() == Languages.Status.INCOMPLETE) txtLangName.hardlight(CharSprite.NEGATIVE);
			add(txtLangName);

			txtLangInfo = PixelScene.renderTextBlock(6);
			if (currLang == Languages.CHINESE) txtLangInfo.text(Messages.get(WndSettings.class,"origin_language"));
			else if (currLang.status() == Languages.Status.REVIEWED) txtLangInfo.text(Messages.get(this, "completed"));
			else if (currLang.status() == Languages.Status.UNREVIEWED) txtLangInfo.text(Messages.get(this, "unreviewed"));
			else if (currLang.status() == Languages.Status.INCOMPLETE) txtLangInfo.text(Messages.get(this, "unfinished"));
			if (currLang.status() == Languages.Status.UNREVIEWED) txtLangInfo.setHightlighting(true, CharSprite.WARNING);
			else if (currLang.status() == Languages.Status.INCOMPLETE) txtLangInfo.setHightlighting(true, CharSprite.NEGATIVE);
			add(txtLangInfo);

			sep2 = new ColorBlock(1, 1, 0xFF000000);
			add(sep2);

			lanBtns = new RedButton[langs.size()];
			for (int i = 0; i < langs.size(); i++){
				final int langIndex = i;
				RedButton btn = new RedButton(Messages.titleCase(langs.get(i).nativeName()), 8){
					@Override
					protected void onClick() {
						super.onClick();
						Messages.setup(langs.get(langIndex));
						ShatteredPixelDungeon.seamlessResetScene(new Game.SceneChangeCallback() {
							@Override
							public void beforeCreate() {
								SPDSettings.language(langs.get(langIndex));
								GameLog.wipe();
								Game.platform.resetGenerators();
							}
							@Override
							public void afterCreate() {
								//do nothing
							}
						});
					}
				};
				if (currLang == langs.get(i)){
					btn.textColor(TITLE_COLOR);
				} else {
					switch (langs.get(i).status()) {
						case INCOMPLETE:
							btn.textColor(0x888888);
							break;
						case UNREVIEWED:
							btn.textColor(0xBBBBBB);
							break;
					}
				}
				lanBtns[i] = btn;
				add(btn);
			}

			sep3 = new ColorBlock(1, 1, 0xFF000000);
			add(sep3);

			txtTranifex = PixelScene.renderTextBlock(6);
			txtTranifex.text(Messages.get(this, "transifex"));
			add(txtTranifex);

			if (currLang != Languages.CHINESE) {
				String credText = Messages.titleCase(Messages.get(this, "credits"));
				btnCredits = new RedButton(credText, credText.length() > 9 ? 6 : 9) {
					@Override
					protected void onClick() {
						super.onClick();
						String creds = "";
						String creds2 = "";
						String[] reviewers = currLang.reviewers();
						String[] translators = currLang.translators();

						ArrayList<String> total = new ArrayList<>();
						total.addAll(Arrays.asList(reviewers));
						total.addAll(Arrays.asList(reviewers));
						total.addAll(Arrays.asList(translators));
						int translatorIdx = reviewers.length;

						//we have 2 columns in wide mode
						boolean wide = (2 * reviewers.length + translators.length) > (PixelScene.landscape() ? 15 : 30);

						int i;
						if (reviewers.length > 0) {
							creds += Messages.titleCase(Messages.get(LangsTab.this, "reviewers"));
							creds2 += "";
							boolean col2 = false;
							for (i = 0; i < total.size(); i++) {
								if (i == translatorIdx){
									creds += "\n\n" + Messages.titleCase(Messages.get(LangsTab.this, "translators"));
									creds2 += "\n\n";
									if (col2) creds2 += "\n";
									col2 = false;
								}
								if (wide && col2) {
									creds2 += "\n-" + total.get(i);
								} else {
									creds += "\n-" + total.get(i);
								}
								col2 = !col2 && wide;
							}
						}

						Window credits = new Window(0, 0, 0, Chrome.get(Chrome.Type.TOAST));

						int w = wide ? 125 : 60;

						RenderedTextBlock title = PixelScene.renderTextBlock(6);
						title.text(Messages.titleCase(Messages.get(LangsTab.this, "credits")), w);
						title.hardlight(SHPX_COLOR);
						title.setPos((w - title.width()) / 2, 0);
						credits.add(title);

						RenderedTextBlock text = PixelScene.renderTextBlock(5);
						text.setHightlighting(false);
						text.text(creds, 65);
						text.setPos(0, title.bottom() + 2);
						credits.add(text);

						if (wide) {
							RenderedTextBlock rightColumn = PixelScene.renderTextBlock(5);
							rightColumn.setHightlighting(false);
							rightColumn.text(creds2, 65);
							rightColumn.setPos(65, title.bottom() + 6);
							credits.add(rightColumn);
						}

						credits.resize(w, (int) text.bottom() + 2);
						ShatteredPixelDungeon.scene().addToFront(credits);
					}
				};
				add(btnCredits);
			}

		}

		@Override
		protected void layout() {
			title.setPos((width - title.width())/2, y + GAP);
			sep1.size(width, 1);
			sep1.y = title.bottom() + 2*GAP;

			txtLangName.setPos( (width - txtLangName.width())/2f, sep1.y + 1 + GAP );
			PixelScene.align(txtLangName);

			txtLangInfo.setPos(0, txtLangName.bottom() + 2*GAP);
			txtLangInfo.maxWidth((int)width);

			y = txtLangInfo.bottom() + GAP;
			int x = 0;

			sep2.size(width, 1);
			sep2.y = y;
			y += 2;

			int cols = PixelScene.landscape() ? COLS_L : COLS_P;
			int btnWidth = (int)Math.floor((width - (cols-1)) / cols);
			for (RedButton btn : lanBtns){
				btn.setRect(x, y, btnWidth, BTN_HEIGHT);
				btn.setPos(x, y);
				x += btnWidth+1;
				if (x + btnWidth > width){
					x = 0;
					y += BTN_HEIGHT+1;
				}
			}
			if (x > 0){
				y += BTN_HEIGHT+1;
			}

			sep3.size(width, 1);
			sep3.y = y;
			y += 2;

			if (btnCredits != null){
				btnCredits.setSize(btnCredits.reqWidth() + 2, 16);
				btnCredits.setPos(width - btnCredits.width(), y);

				txtTranifex.setPos(0, y);
				txtTranifex.maxWidth((int)btnCredits.left());

				height = Math.max(btnCredits.bottom(), txtTranifex.bottom());
			} else {
				txtTranifex.setPos(0, y);
				txtTranifex.maxWidth((int)width);

				height = txtTranifex.bottom();
			}

		}
	}
}
