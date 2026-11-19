# Independent source verdict

Verdict: **ACCEPT**, scoped to the corrected source claims below.

## Binding

- Draft revision: `4a117862640b7dc7d093c84d5e2ada677586d341`
- Draft path: `development/research/findings/1.13.2--1.14.4/eye-submersion-context-and-chunk-gate.md`
- Draft byte SHA-256: `b50ca3673e0ca462fb5f7a607dd98fc88effd411074fb27dd7473869d7f75957`

## Accepted bounded claims

In 26.2, `EntityFluidInteraction.update` resets its trackers and admits scanning only when its fluid-interaction box plus one-block X/Z padding passes the FULL-chunk and fluid-section gate. A missing padded adjacent chunk can return before scanning, leaving the eye tracker false despite an otherwise loaded wet eye cell. This is a native source gate; it does not establish a product defect.

The latches and consumers remain distinct. `Entity.baseTick` reads `wasEyeInWater` before refreshing the interaction tracker and then updates swimming. Generic `Entity.isUnderWater` combines the prior eye latch with current body-water; generic swimming entry additionally requires sprinting, non-passenger status, and WATER at the current block position. `Player.updateIsUnderwater` writes the separate Player `wasUnderwater` cache before `super.tick`; `LocalPlayer.isUnderWater` overrides the generic method to return that cache. The draft identifies LocalPlayer sprint-start eligibility as a 26.2 target contrast, not a historical consumer contract.

The source comparison does not broaden the historical point-query or client `isChunkLoaded` dispositions. Historical movement-reader predicates are separately identified. No product applicability or implementation requirement is inferred from the native gate.

## Reviewed source identities

- 1.13.2, Ornithe Feather Gen2 build 2; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; checked `Entity.java` SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- 1.14, Yarn; the available source tree had no ready/provenance manifest. Checked `Entity.java` SHA-256 `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a`; `ClientPlayerEntity.java` SHA-256 `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be`.
- 1.14.4, Ornithe Feather Gen2 build 2; source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; checked `Entity.java` SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- 26.2, unobfuscated; source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; checked `Entity.java` SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`, `EntityFluidInteraction.java` SHA-256 `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`, `Player.java` SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`, and `LocalPlayer.java` SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

The immutable draft hash was independently recomputed during the rebind. This verdict covers only the corrected claims and evidence identities above.
