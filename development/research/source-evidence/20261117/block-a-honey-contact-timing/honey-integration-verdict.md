# Honey contact timing integration verdict

Accepted for the bounded product integration. Ready ref refs/campaign-ready/lead-a-honey-contact-timing resolves to 3aecd25b23e8d49c198fa6e0d9ebc5daee222a85, tree 8a5d1edb6838cdfb3de7155c62f43063401e8198, with direct product parent 30aaee28da28e399309589862dca4843a97b1dd8. Independent candidate 36517936fdc7ab784d2bdaa616f6dc767a792bf1 was reconciled into existing product seams. Accepted source evidence commit 8b51cbcf67797dd8eb41e6c3148be554a196eb4b is an ancestor of the product commit. The evidence is under development/research/source-evidence/20261117/block-a-honey-contact-timing/.

## Applicability and dispatch

The V1_19 alias comprises 1.19, 1.19.1 and 1.19.2. Provider V1_19_3 covers that alias and exact 1.19.3. V1_18_2 fences off earlier profiles and 1.19.4 and later keep their existing handling. The replay applies to a locally authoritative standing, dry, airborne, unmounted LocalPlayer SELF move in ordinary air travel with matching requested and resolved displacement and a single recorded movement. The air-travel guard excludes ability flight, fall flight, no gravity, slow falling, levitation and discarded friction. Unsupported, non-player and logical-server paths retain existing dispatch.

At the existing Entity.move dispatch point, after the position and box write and before getBlockSpeedFactor, the replay enumerates the loaded final box in historical X/Y/Z order. It dispatches only Honey's inside callback. The independently selected inset is 0.001 for V1_19 and 1.0E-7 for V1_19_3. The existing HoneyBlock gate and math govern the horizontal factor, Y write and fall-distance reset. Ordinary gravity and drag follow the move. A successful loaded scan records the exact singleton move. Only a post-travel collector with that identical lone record suppresses the late Honey slide gate, so there is a single reset and the other block callbacks still run. If an unloaded scan or unsupported move prevents coverage, it leaves the existing route available. The tick, air travel and collector restore transient context in finally blocks.

Static checks verified the native callback, move and collector owners and descriptors, packaged client mixin registration and versioned providers. At the source witness the X lower floor is 1 with the older inset and 0 with the newer inset. At initial Y velocity -0.2 the corresponding ordinary-travel end values are approximately -0.2744000053405762 and -0.12740000247955321. The callback can scale X/Z by 0.25 before subsequent travel arithmetic. The code association is bounded to that Honey operation. Input-only reachability, general contact traversal and runtime parity remain unverified.

## Intended finding association

Associate this bounded contact and timing implementation with development/research/findings/1.19.2--1.19.3/honey-contact-scan-boundary.md. The operation is implemented by HoneyContactTiming, NativeHoneyContactTiming, HoneyContactTimingHook and HoneyContactScope. The timing sites are EntityMixin and LivingEntityMixin, and the response route is HoneyBlockMixin. LoadedFinalBoxContactScan shares the existing traversal with FinalBoxEntityBlockContactTraversal.

## Changed Java paths

- src/client/java/me/wolfii/legacyparkourcompat/change/v1_18_2/NativeHoneyContactTiming.java
- src/client/java/me/wolfii/legacyparkourcompat/change/v1_19_3/HoneyContactTiming.java
- src/client/java/me/wolfii/legacyparkourcompat/change/v1_21/entity/FinalBoxEntityBlockContactTraversal.java
- src/client/java/me/wolfii/legacyparkourcompat/mechanic/HoneyContactScope.java
- src/client/java/me/wolfii/legacyparkourcompat/mechanic/LoadedFinalBoxContactScan.java
- src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/block/HoneyContactTimingHook.java
- src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java
- src/client/java/me/wolfii/legacyparkourcompat/mixin/HoneyBlockMixin.java
- src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java

The exact-tree tests-disabled build passed. Its receipt is honey-build-receipt.md, SHA-256 a4e3596e71670e1bbbcc55900610aafc28260aa0126f8119e761ec6536ae73ce. No new external dependencies or vendor declarations apply.
