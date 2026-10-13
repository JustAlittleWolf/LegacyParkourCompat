# MCPK 1.17 Big Dripleaf: implementation reconciliation

## Disposition

**Already native where available; intentionally excluded before 1.17.** No mod code is needed for this finding. The source pair remains partial; this reconciliation does not close discovery coverage or establish runtime parity.

## Accepted evidence

- Immutable snapshot: `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot-r2.md`, content SHA-256 `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee`.
- Clean independent review: `6e045f3d50f9248ab329c5bd698caa935e8a9be3:workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-dispositions-2026-10-08.md`; disposition **ACCEPT**.
- The preserved MCPK catalog describes the tilt timing, but its page fetch was HTTP 403. The source-backed reconciliation below relies on the exact vanilla sources, not the page wording.

## Source reconciliation

Canonical sources are the read-only `build/movement-campaign-2026-10-07/ready/` trees. The 1.16.5 `Blocks.java` has no Big Dripleaf registration (SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`). The block is registered in 1.17.1 `Blocks.java` (SHA-256 `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`); its `BigDripleafBlock.java` SHA-256 is `1e1315b0a53eb85a3365335131e7819c1b667a55e14343d839c5211c671fd59f`.

For 1.17.1, the collision shape is leaf-only: `NONE` and `UNSTABLE` use Y=11/16..15/16, `PARTIAL` uses Y=11/16..13/16, and `FULL` is empty. `entityInside` can set `UNSTABLE`; server block ticks transition `UNSTABLE -> PARTIAL -> FULL` with 10 ticks per transition. These block states and scheduled ticks are vanilla lifecycle.

Current target source is 26.2 (`unobfuscated`). `Blocks.java` registers Big Dripleaf (SHA-256 `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`) and `BigDripleafBlock.java` has SHA-256 `6442924185357e52fb41d110596766cc98140058e5484af7c1ea04610bed816d`. Its collision provider still returns only the leaf map: `NONE`/`UNSTABLE` use `Block.column(16, 11, 15)`, `PARTIAL` uses `Block.column(16, 11, 13)`, and `FULL` is empty. The delay map still schedules `UNSTABLE` and `PARTIAL` after 10 ticks. `getShape` also includes the stem, but that outline shape is separate from `getCollisionShape` and does not change player collision here.

Therefore the eligible Big Dripleaf player collision shape and the described tilt timing are already native in 26.2. Since the block did not exist in 1.16.5, adding it to profiles at or below 1.16 would invent historical behavior. No block states, registration, callback, or tick lifecycle were changed.

## Identity and limits

- Snapshot endpoint manifests: 1.16.5 source `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, artifact `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`; 1.17.1 source `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`, artifact `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`.
- Current 26.2 source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- No build, tests, or runtime simulation were run, per assignment. The broader source pair remains partial and runtime validation is unperformed.
