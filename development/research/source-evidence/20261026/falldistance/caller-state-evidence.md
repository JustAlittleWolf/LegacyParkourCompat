# Source-only correction evidence

This revision supersedes the claim in `.native-source/resume-20261023/client-fall-distance-state/finding.md`; the earlier document remains untouched. Its retained-high-distance-after-landing witness is invalid: the new gate already suppresses the ordinary airborne accumulation needed to establish that retained distance.

## Closed callback and consumer gates

Both compared releases use LocalPlayer -> Player.travel -> LivingEntity.travel -> travelInAir -> handleRelativeFrictionAndCalculateMovement -> `move(SELF, deltaMovement)` -> Player.maybeBackOffFromEdge -> collide -> update onGround -> checkFallDamage (the last call only on 1.21.3 for this player). `LocalPlayer.move` delegates before autojump bookkeeping; disable autojump. LocalPlayer.isEffectiveAi returns true, Entity.isControlledByLocalInstance consequently true without a controlling passenger, and Player.isControlledByClient returns true. Logical client, local controlled camera, alive, no passenger, no flying/gliding/swimming, loaded chunks, standard gravity/step height, no effects, no collision-altering equipment, no fluids, no climbable, no stuck multiplier, no external movement/correction/reset, no nearby entity collisions. Normal full blocks and a closed bottom trapdoor do not reset fall distance while traversing air. The super call in LivingEntity.checkFallDamage is unconditional after its water and server-particle branches. Whole-tree search finds `doCheckFallDamage` callers only in ServerGamePacketListenerImpl. `checkSlowFallDistance` is reached for gliding or leash handling, neither present here.

The source consumer is unchanged: 1.21.3 Player1066-1124 / 1.21.4 Player1069-1127. It requires flying=false, requested y<=0, SELF/PLAYER mover, shift held, and isAboveGround. Player.isStayingOnGroundSurface returns isShiftKeyDown; LocalPlayer reads the shift keyPresses bit. LocalPlayer.serverAiStep copies movement impulses and jump bit; zero movement impulses remain zero through sneak-speed and0.98 scaling. LivingEntity's `moveRelative` contributes zero for those impulses. The default step height0.6 is converted to float by maxUpStep. No unit tests or simulation were run: numbers below are static evaluation of the decompiled recurrence only.

## Conditional numeric trajectory before landing

Geometry: finite full floor occupies x[-1,1], z[0,1], y[-1,0]. No other relevant collision shapes. Player width is0.6F (EntityType1034-1039), including crouching width; center z=.5.

Common initial caller state is deliberately conditional and airborne: onGround=false; feet y=2.1875; fallDistance=0; horizontal velocity x=.3,z=0; vertical velocity `(-.08)*double(.98F)=-.0784000015258789`; x=`1.28 - 1.4404359189558162 = -.1604359189558162`. For ticks1â€“6 all movement/jump/shift keys are up; tick7 only shift is down. Disable autojump. No upper support or landing is used to establish the divergence. No preceding ordinary-input history establishing this complete airborne state is claimed.

For this caller state the source recurrence is `vyNext=(vy-.08)*double(.98F)` and horizontal drag is double(.91F). All move lengths are below1, so the long-move fall-reset raycast does not execute. Old fall distance adds each cast-to-float negative collision-resolved y; new remainszero. These are source arithmetic evaluations, not a game run.

| Tick | Feet y before move | Requested y | Old fallDistance before move | Requested x |
| --- | --- | --- | --- | --- |
|1|2.1875|-.0784000015258789|0|.3|
|2|2.109099998474121|-.1552320045166016|.0784|.2730000078678131|
|3|1.9538679939575196|-.230527368912964|.233632|.24843001431942005|
|4|1.7233406250445555|-.30431682745754424|.46415937|.22607131954600865|
|5|1.4190237975870112|-.37663049823865513|.7684762|.20572490671582416|
|6|1.042393299348356|-.44749789698341763|1.1451067|.1872096705067504|
|7|.5948954023649384|-.5169479491049732|1.5926046|.17036080507091186|

At tick7 center x=1.28, minX approximately.979999988. Both are airborne. Old distance exceeds.6F, so isAboveGround=false and x stays.170360805. New distance0 is below.6F, and the downward support prism reaches yâ‰ˆ-.0051146, overlapping the lower full floor; isAboveGround=true. The x loop tests.170360805,.120360805,.070360805,.020360805; even the last prism begins beyond floor x1. At that last candidate abs(x)<=.05, so x becomes0. Both vertical destinations are yâ‰ˆ.0779474532599652, strictly above floor top0: the consequence occurs before landing. The old footprint leaves the floor; the new remains above its edge.

Open reachability gate: no complete preceding ordinary input sequence yielding this full initial airborne state has been reconstructed. The table is an explicit conditional caller-state witness, not proof of exact ordinary gameplay reachability. The corrected source claim must remain narrower until this antecedent is independently closed. There is no remaining claim that1.21.4 ordinarily accumulates the earlier high fall distance.
## Exact stable return boundary and separation from precision

1.21.3 has the old callback and float field;1.21.4 skips it and retains float.1.21.5 restores the callback through local client authority and changes the field to double. `checkFallDamage` still casts each y contribution to float on1.21.5 and26.2; that is a separate arithmetic operation from the client call gate. No concrete precision-induced movement witness is asserted here.

Inspected restoration persists in every requested later exact release:1.21.5 Entity702-703/3162-3169 Player1312-1314;1.21.6â€“1.21.8 Entity719-720/3335-3342 Player1324-1326;1.21.9 Entity732-733/3401-3408 Player1177-1179;1.21.10 Entity735-736/3406-3413 Player1177-1179;1.21.11 Entity750-751/3424-3431 Player1242-1244;26.1,26.1.1,26.1.2 Entity769-770/3516-3523 Player1260-1262;26.2 Entity777-778/3589-3595 Player1281-1283. Each Player override returns isLocalPlayer; LocalPlayer override returns true. Thus stable suppression introduction1.21.4 and stable restoration1.21.5 are directly bounded by adjacent exact stable releases. No snapshot boundary is claimed.

## Evidence identity

Canonical root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft` and was read-only. `evidence-identities.json` records source/publication paths and SHA256 identities. Releases with ready/source manifests had both manifest hashes checked against the ready marker and every used Java file checked against the source manifest. Other releases had every used file checked against their `.decompile-provenance.json` sources dictionary, with exact client, mapping and version metadata input URL/hash identities retained. All13 releases passed these checks. This does not independently revalidate every binary artifact in the preparation cache. No product implementation, wiki, history or transcript was read. No code/build/test/game/source-generation action was performed.
