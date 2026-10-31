from pathlib import Path
p=Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/member-dispositions-raw.tsv')
s=p.read_text(encoding='utf-8-sig')
s=s.replace('1.19->1.19.1\tnet/minecraft/world/entity/LivingEntity.java\ttick / aiStep removal guard', '1.19->1.19.1\tnet/minecraft/client/player/LocalPlayer.java\taiStep input route\t725\t731\tterminal-no-input-delta; Input call and sprint/crouch path preserved\tinput_provider_closure/closure.md\n1.19->1.19.1\tnet/minecraft/world/entity/LivingEntity.java\ttick / aiStep removal guard')
s=s.replace('1.19.3->1.19.4\tnet/minecraft/world/entity/Entity.java\tisControlledByLocalInstance / LocalPlayer.isEffectiveAi\t2742,2805\t2795,486\tterminal-for-LocalPlayer travel; local player override returns true in both versions; generic gate expansion does not change player travel\tcore-path comparison',
'''1.19.3->1.19.4\tnet/minecraft/world/entity/Entity.java\tisControlledByLocalInstance\t2742\t2795\tterminal-for-LocalPlayer travel; changed fallback delegates to isEffectiveAi() absent a controlled player passenger\tcore-path comparison
1.19.3->1.19.4\tnet/minecraft/client/player/LocalPlayer.java\tisEffectiveAi\t493\t486\tterminal-unchanged-override; returns true on both sides and keeps LivingEntity.travel gate true\tcore-path comparison''')
p.write_text(s,encoding='utf-8')
