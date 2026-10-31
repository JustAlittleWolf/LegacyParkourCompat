from pathlib import Path
p=Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/member-dispositions-raw.tsv')
s=p.read_text(encoding='utf-8-sig')
s=s.replace('updateFallFlying\t2626\t2658','updateFallFlying\t2590\t2623')
s=s.replace('tick / outbound sprint packet\t188,194-198\t185,193-198','tick / outbound sprint packet\t200\t188')
s=s.replace('aiStep / mounted low-food sprint gate\t652,693-727\t652,693-727','aiStep / mounted low-food sprint gate\t581,771-795\t652,693-727')
s=s.replace('updateAutoJump\t911\t891','updateAutoJump\t909\t891')
s=s.replace('maybeBackOffFromEdge / isAboveGround\t1047-1079\t1038-1070','maybeBackOffFromEdge / isAboveGround\t1047,1102\t1038,1093')
p.write_text(s,encoding='utf-8')
