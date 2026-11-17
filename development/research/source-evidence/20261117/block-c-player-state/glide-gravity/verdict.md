# Independent source audit: Elytra gravity range

**Verdict: COMPLETED**

Bound source draft SHA-256: `c5b9d3d2bb70cef64102b37ee9e99a3420dfe8d2367ea489b5d61422a6c6e31d`

## Findings

The bounded claim is confirmed for the exact 1.20.4 → 1.20.5 pair. In 1.20.4 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `travel` initializes its local gravity to `0.08` (line 2037) and the fall-flying vertical term uses that local (line 2109). In 1.20.5 the same method initializes it from `this.getGravity()` (line 2106), applies `Math.min($$1, 0.01)` for downward slow falling (line 2109), and uses it in the fall-flying term (line 2178). The term’s formula and following lift, steering, drag, and movement ordering match across these excerpts. Thus the source change is between these exact endpoints; this audit does not infer a finer patch boundary.

In 1.20.5, `Entity.getGravity()` returns zero when `isNoGravity()` is true, otherwise it delegates to `getDefaultGravity()` (`Entity.java:1106`). `LivingEntity.getDefaultGravity()` reads `Attributes.GRAVITY` (`LivingEntity.java:2100`; attribute declaration in `ai/attributes/Attributes.java:42`). The ordinary default base is 0.08 (`LivingEntity.java:146`). Therefore the new term is not just a no-gravity check: it also reads the newer generic gravity attribute. Customized attribute values are outside this audit’s requested scope. In 1.20.4 the fall-flying term uses the fixed local even when the no-gravity flag is true.

For player reachability in 1.20.4, `Entity` defines `DATA_NO_GRAVITY` false in its constructor (`Entity.java:257`) and exposes generic `isNoGravity`/`setNoGravity` accessors (`1092-1097`). A whole-source search of the exact 1.20.4 decompile found no `setNoGravity` call in `Player`, `LocalPlayer`, `ServerPlayer`, or the glide packet handler, nor a vanilla ordinary player movement/glide path that sets the flag true. The ordinary Elytra start path sets the separate shared fall-flying bit. Consequently, ordinary vanilla player behavior does not produce a true no-gravity state for gliding.

There is an important supplied-state route: generic `Entity.load` calls `setNoGravity(tag.getBoolean("NoGravity"))` (`Entity.java:1697`), and `Player.readAdditionalSaveData` calls `super.readAdditionalSaveData` (`Player.java:751-752`). A player loaded from NBT whose `NoGravity` field is true can therefore hold true; the entity save path also writes the field when true (`Entity.java:1592-1593`). This proves vanilla handling/preservation of supplied or already-set player state, not that ordinary vanilla player logic creates it. The 1.20.4 fixed Elytra term still does not consume that flag.

## Evidence identity and limits

Canonical read-only roots: `decompiled_minecraft/1.20.4/mojmap` and `decompiled_minecraft/1.20.5/mojmap`. Their ready markers identify Mojmap sources with manifest SHA-256 values `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` and `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`, respectively; client SHA-256 values are `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` and `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`. The relevant 1.20.4 and 1.20.5 `LivingEntity.java`/`Entity.java` files were present and their contents matched the cited bodies.

This is an independent audit of the gravity operation and bounded player no-gravity reachability only. It does not establish full-pair movement coverage, source acceptance, implementation or runtime parity, custom gravity attribute behavior, mod/plugin callers, or a more precise code-introduction boundary than 1.20.4 to 1.20.5. No code, tests, runtime, or source generation were performed.

**Terminal status: COMPLETED**
