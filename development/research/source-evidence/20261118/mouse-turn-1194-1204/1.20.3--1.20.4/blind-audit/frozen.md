# Blind source audit: 1.20.3 → 1.20.4

## Scope and result

Bounded audit of ordinary local captured mouse callbacks with finite deltas, smooth camera off and not scoping; Entity.turn and its virtual pitch setter/clamp; and one reachable, active creative-flight travel through its dry-air movement branch. Device plumbing is excluded.

**Result: bounded equivalence; no movement delta found in these slices.** Every consumed Java source file listed below has identical bytes at both endpoints, and its actual SHA-256 matches each side's source manifest/provenance. This is a bounded result, not full-pair movement coverage or runtime parity.

## Exact source identity

Read-only canonical roots:
- D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.3/mojmap/
- D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.4/mojmap/

Mappings are Mojmap (official Mojang client mappings). The 1.20.3 embedded .decompile-provenance.json SHA-256 is c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f; its pinned client input is bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655, official mapping input (client.txt) is ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f, and exact 1.20.3 version metadata input is 05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2.

For 1.20.4, the version-root mojmap.ready.json identifies exact version 1.20.4, status ready, and Mojmap; its SHA-256 is 6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1. It pins client SHA-256 9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838; the version-root artifact manifest pins client_mappings.txt to ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f, client-mojmap.jar to d20c183016505cceecd33d508973e7ca6f2ee8cbd860f316592f4a09a96bc339, and version.json to 48131d228cdaee5087bc9179ad5ed1ae39d0e52750609bdb2cf0596f20d3bdc6. Version-root manifest SHA-256s: mojmap.sources.sha256 = fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1; artifacts.sha256 = ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948; mojmap.provenance.json = 10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385.

The following full-file hashes were read from actual bytes and matched the corresponding 1.20.3 embedded source map and 1.20.4 mojmap.sources.sha256; each pair is equal:

| Relative source path | SHA-256 at both endpoints |
|---|---|
| net/minecraft/client/MouseHandler.java | a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56 |
| net/minecraft/client/Minecraft.java | 4c672a48906a76408bfbf77fda303d6cead7b22c6e4632aed7a5d8182e057b0a |
| net/minecraft/client/player/LocalPlayer.java | bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5 |
| net/minecraft/client/player/Input.java | b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb |
| net/minecraft/client/player/KeyboardInput.java | a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0 |
| net/minecraft/world/entity/Entity.java | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 |
| net/minecraft/world/entity/LivingEntity.java | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d |
| net/minecraft/world/entity/player/Player.java | 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd |
| net/minecraft/world/entity/player/Abilities.java | a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc |

## Compared behavior

### Callback delta and order

In MouseHandler.onMove (1.20.3/1.20.4, lines 187–222), a matching-window event seeds xpos/ypos if ignoreFirstMove is set, then continues. In the ordinary captured and active-window branch it accumulates x - xpos and y - ypos; it calls turnPlayer() before updating xpos/ypos. For the requested finite, local captured case, turnPlayer first computes elapsed event time from Blaze3D.getTime() and updates lastMouseEventTime. It gates rotation on mouse grabbed plus active window, then computes sensitivity as options.sensitivity().get() * 0.6F + 0.2F, cubes it in double, and multiplies by 8.0. With smooth camera off and not first-person scoping, it resets both smooth filters and sets deltas to accumulated X/Y multiplied by that factor. It then zeros both accumulated values, applies the integer Y inversion sign, calls tutorial onMouse, and calls player.turn(x, y * sign) only when player is non-null. UI callbacks do not alter this scoped claim; window/device event plumbing is outside it.

The bodies, including those operation and gate orders, are byte-identical at both versions (MouseHandler SHA above). No callback-delta difference is found for the bounded conditions.

### Entity.turn and pitch dispatch

Entity.turn(double,double) (Entity.java lines 398–410) casts each input to float before multiplying by 0.15F; vertical delta is applied first through this.setXRot(this.getXRot() + vertical), horizontal through this.setYRot(this.getYRot() + horizontal), then pitch is clamped with Mth.clamp(..., -90.0F, 90.0F). It next adds the un-clamped deltas to xRotO/yRotO, clamps xRotO, then calls vehicle.onPassengerTurned(this) if mounted. The pitch calls are virtual setter calls. In inherited Entity.setXRot(float) (lines 3309–3315), non-finite input is logged/discarded and finite input assigns xRot; LocalPlayer, Player, and LivingEntity declare no setXRot override. The setter, clamp, and preceding/following writes are in the identical Entity.java bodies at both endpoints.

### First active flight movement

A reachable witness is a locally controlled creative player with mayfly/flying enabled, not a passenger, in ordinary dry air, not fall-flying, with movement input. The preceding finite mouse callback changes the same local player's yaw through Entity.turn; on the next local player tick the path reaches the same flight travel body at both versions.

Relevant gates and order:
- LocalPlayer.aiStep (LocalPlayer.java lines 646–805) refreshes input, and when mayfly is allowed handles the jump-edge toggle; flying && isControlledCamera() applies the jump/shift vertical impulse as signedCount * abilities.getFlyingSpeed() * 3.0F before super.aiStep().
- LivingEntity.aiStep (LivingEntity.java around lines 2511–2603) forms travel input from xxa/yya/zza after horizontal input multipliers, then calls this.travel(input) when there is no controlling passenger. Its isControlledByLocalInstance() gate controls the local travel body.
- Player.travel (Player.java lines 1451–1473) first has its separate swimming impulse gate. The active creative-flight branch is exactly abilities.flying && !isPassenger(): save current Y velocity, call super.travel(input), restore Y to the saved value times 0.6, reset fall distance, and clear shared flag 7.
- For the bounded dry-air witness, LivingEntity.travel (LivingEntity.java lines 2035–2165) rejects the water, lava and fall-flying branches and reaches the ordinary branch. It selects below-block friction; chooses onGround ? friction * 0.91F : 0.91F; computes movement with handleRelativeFrictionAndCalculateMovement; adjusts Y for levitation, missing client chunk, or gravity; and applies horizontal/vertical friction unless friction is discarded. The helper (lines 2202–2214) uses ground speed scaled by 0.21600002F / friction³, or airborne getFlyingSpeed; it calls moveRelative, then move(MoverType.SELF, deltaMovement). Entity.moveRelative/getInputVector (Entity.java lines 1295–1310) normalizes input only when length squared exceeds one, scales it, computes float sine/cosine from yaw, and rotates X/Z. Player.getFlyingSpeed (Player.java lines 2064–2071) gates ability flying and passenger state, doubling ability speed while sprinting; Abilities.getFlyingSpeed returns the configured float.
- After the superclass travel returns, Player's flight branch restores Y velocity to savedY * 0.6; thus the temporary vertical result from the superclass is discarded, while the self move already occurred with its pre-reset delta. These same orderings and gates apply to both endpoint versions because all listed source files are byte-identical.

The source slice establishes a reachable dependence of horizontal flight input on the just-updated yaw, but no version delta. This is source reachability/equivalence only; no gameplay or runtime trajectory was executed.

## Residual boundary

This audit does not enumerate the full player tick graph, every movement mode, the complete downstream world/block collision-shape provider graph, or other direct player responses. Device plumbing, UI behavior outside the callback conditions above, and runtime parity are outside this bounded artifact. These residuals are not claims of pair-wide coverage or acceptance.