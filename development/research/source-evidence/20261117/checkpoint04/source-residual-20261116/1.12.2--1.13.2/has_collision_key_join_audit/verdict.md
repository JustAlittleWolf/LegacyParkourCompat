# Independent source audit verdict: base `hasCollision` exact-key join

## Verdict: ACCEPT

This verdict is bound to the exact evidence below. The reviewed report and CSV bytes match their supplied SHA-256 identities. No correction is required for the bounded claims.

## Immutable evidence identity

- Report: `build/source-residual-20261116/1.12.2--1.13.2/has_collision_key_join/report.md` — SHA-256 `FED592C29BE830F0AE227C695E14541D4F77BECDC3E88A4F81F45FD97F31A815`.
- CSV: `build/source-residual-20261116/1.12.2--1.13.2/has_collision_key_join/key-join.csv` — SHA-256 `C6BD748D0863EA4BC7F6098D0268D579B8DA22216BE23086400FFCD45CE608F5`.
- 1.12.2 source manifest / ready marker: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 source manifest / ready marker: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Reused exact-key crosswalk: `build/source-drafts/registry-collision-crosswalk.md` — SHA-256 `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`.
- Crosswalk inputs: `registry-collision-1122.md` — SHA-256 `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`; `registry-collision-1132.md` — SHA-256 `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`.

The local source-manifest bytes, both ready-marker declarations, the cited Java file bytes, and the report/CSV/crosswalk/inventory hashes were checked. All supplied identities match. Consulted Java files also match their cited manifest entries: 1.12.2 `Block.java` (`e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`), `EndPortalBlock.java` (`b091d0fbf32b3bf18090fac68c876657e518d986eac7236ef0eb19cdacebf8e6`); 1.13.2 `Block.java` (`735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`), `state/BlockState.java` (`0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`), and `EndPortalBlock.java` (`9541e703d04bf9148cb324741de73c4f3ce76021bc358f22308c946bccb72ad3`).

## Audit findings

- Independently filtered the crosswalk's exact-key rows to old effective `addCollisions` owner `Block`, old effective `getCollisionShape` owner `Block`, and new route beginning `Block.getCollisionShape`. This yields exactly **115** keys. CSV comparison found exactly the same 115 unique keys, with no missing or extra keys and no mismatches in the six key/ID/class/owner/route fields. Every CSV `hasCollision` value is `true`.
- Checked each of the 115 CSV `registrationLine` references against the 1.13.2 `Block.java` balanced registration call. Each call contains the registered key and none contains `.noCollision()`.
- The 1.13.2 source supports the property inference: `Block.Properties.hasCollision` initializes to `true` (`Block.java:1901`); `noCollision()` sets it false (`1943–1945`); the `Block(Properties)` constructor copies the field (`224–231`); base `getCollisionShape` returns `state.getShape(...)` only when the registered instance flag is true (`393–394`). `BlockState.getCollisionShape` delegates to its block (`state/BlockState.java:209–210`). Thus the roster-filter conclusion that all 115 selected instances have `hasCollision=true` is supported.
- `minecraft:end_portal` is correctly excluded from the old-base-owner subset: the crosswalk lists old effective `EndPortalBlock.addCollisions`, old `Block.getCollisionShape`, and the new base collision-shape route. In 1.12.2, the override at `EndPortalBlock.java:44` has an empty body; its separate `getShape` returns the 1×0.75×1 box (`:34–35`). `World.getCollisions` calls the state's `addCollisions` callback (`World.java:964–1001`), so that old movement-collision callback adds no obstacle for this block. In 1.13.2, the `end_portal` registration applies `.noCollision()` (`Block.java:1217–1219`), so the inherited base collision method returns empty before using `EndPortalBlock.getShape`. This supports the report's narrow exception explanation: it is not a prior nonempty default collision emitted by old `Block.addCollisions` newly suppressed by the flag.

## Scope boundary

Acceptance covers only the stated exact-key base-dispatch filter, the `hasCollision` inference for those 115 registrations, and the `end_portal` exception. It does not establish equality of their collision geometry/state behavior, resolve aliases or keys outside the exact intersection, verify specialized `getShape` providers, close unrelated dependencies, or establish full pair coverage.
