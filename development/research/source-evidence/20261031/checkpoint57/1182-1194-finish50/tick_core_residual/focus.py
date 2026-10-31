import re,pathlib,difflib
root=pathlib.Path(r'D:\Javastuff\LegacyParkourCompat\decompiled_minecraft'); vs=['1.18.2','1.19','1.19.1','1.19.2','1.19.3','1.19.4']
paths=['net/minecraft/client/Minecraft.java','net/minecraft/client/multiplayer/ClientLevel.java','net/minecraft/client/player/LocalPlayer.java','net/minecraft/world/entity/player/Player.java','net/minecraft/world/entity/LivingEntity.java','net/minecraft/world/entity/Entity.java','net/minecraft/client/player/Input.java','net/minecraft/client/player/KeyboardInput.java']
stripper=re.compile(r'//[^\n]*|/\*.*?\*/|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'',re.S)
def ext(path,ver):
 s=(root/ver/'mojmap'/path).read_text(encoding='utf8'); m=stripper.sub(lambda x: ''.join('\n' if c=='\n' else ' ' for c in x.group()),s); out={}; i=0; d=0
 while i<len(m):
  if m[i]=='{':d+=1;i+=1;continue
  if m[i]=='}':d-=1;i+=1;continue
  if d==1:
   j=i
   while j<len(m) and m[j] not in '{};':j+=1
   if j<len(m) and m[j]=='{' and '(' in m[i:j]:
    h=s[i:j].strip(); mm=re.search(r'([A-Za-z_$][\w$]*)\s*\([^()]*\)\s*(?:throws\s+[^\{]+)?$',h,re.S)
    if mm and re.search(r'\b(public|protected|private)\b',h):
     k=j+1; bd=1
     while k<len(m) and bd:
      if m[k]=='{':bd+=1
      elif m[k]=='}':bd-=1
      k+=1
     ident=re.sub(r'\s+',' ',mm.group(0).split('throws')[0].strip()); out[ident]=s[i:k]
     i=k;continue
    i=j+1;continue
   i=j+1;continue
  i+=1
 return out
selected={'Minecraft.java':['runTick','tick','handleKeybinds'],'ClientLevel.java':['tick','tickEntities','tickNonPassenger','tickPassenger','handleBlockChangedAck','syncBlockState','setServerVerifiedBlockState'],'LocalPlayer.java':['tick','aiStep','move','updateAutoJump','moveTowardsClosestSpace','setSprinting','handleNetherPortalClient','jumpableVehicle'],'Player.java':['tick','aiStep','travel','jumpFromGround','maybeBackOffFromEdge','isAboveGround','setItemSlot'],'LivingEntity.java':['tick','aiStep','travel','updateFallFlying','handleOnClimbable','getJumpPower','getJumpBoostPower','getFluidFallingAdjustedMovement','isEffectiveAi','tickRidden','travelRidden'],'Entity.java':['tick','baseTick','move','maybeBackOffFromEdge','checkFallDamage','checkInsideBlocks','collide','getBlockPosBelowThatAffectsMyMovement'],'Input.java':['tick','getMoveVector','hasForwardImpulse'],'KeyboardInput.java':['tick']}
out=[]
for a,b in zip(vs,vs[1:]):
 out.append(f'## {a} -> {b}')
 for p in paths:
  if p.split('/')[-1] not in selected:continue
  old=ext(p,a);new=ext(p,b)
  for ka in selected[p.split('/')[-1]]:
   os=[(k,v) for k,v in old.items() if k.startswith(ka+'(') or k==ka+'()']; ns=[(k,v) for k,v in new.items() if k.startswith(ka+'(') or k==ka+'()']
   for key in sorted(set(k for k,v in os)|set(k for k,v in ns)):
    ov=dict(os).get(key);nv=dict(ns).get(key)
    if ov==nv:continue
    out.append(f'### {p} :: {key}')
    if ov and nv: out.extend(difflib.unified_diff(ov.splitlines(),nv.splitlines(),fromfile=a,tofile=b,lineterm=''))
    else: out.extend((ov or nv).splitlines())
pathlib.Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/focused-diffs.txt').write_text('\n'.join(out),encoding='utf8')
