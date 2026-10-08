# Movement campaign integration queue — 2026-10-08

Integration branch: `feat/movement-campaign-integration-2026-10-08`.
Baseline: `457b350fa5490b90b08de4f97033718c77efec1d` (`feat/movement-completeness-final`; final 2026-10-07 checkpoint). This queue records incremental handoffs from that baseline. Existing code coverage, new implementation, static review, integration/build, and runtime validation are separate statuses.

| Finding/work item | Source evidence / disposition | Baseline or incoming code | Static review | Integration / build | Runtime |
|---|---|---|---|---|---|
| 1.15 sprint reset + water descent | Owner verified both are already present in baseline; bytecode ordinal and `isAffectedByFluids` call/profile fallback traced. No new commit needed. | Existing coverage at baseline; no duplicate patch. | Static binding verified by owner; not runtime proof. | No integration needed; new campaign build pending. | Not performed. |
| F041, 1.19 fall-flying sprint gate | Owner verified existing baseline coverage in `v1_19SprintFallFlyingGate` / `SprintStart`, `v1_19_4SprintStart`, and `LocalPlayerMixin.canStartSprinting`. | Existing coverage at baseline; not a new fix. | Static binding verified by owner. | No integration needed; new campaign build pending. | Not performed. |
| TICK01 sprint duration | Owner is tracing the existing `SprintDuration` path. | Existing baseline code under review; no new patch reported. | Pending owner trace. | Pending disposition. | Not performed. |
| F002 fixed-profile pose selection / collision-fit | Accepted source snapshot `448934e826fb41266dc79a313ef1187899d76382`; blind acceptance `10aa24e1114200397ed2d042a33f269113faf179`. | Implementation underway. | Pending exact-code review. | Pending. | Not performed. |
| Sprint timeout ordering | Incoming code commit `3c531180287b3cecff49e557083db73352b258f7` moves existing sprint-duration timer dispatch from `aiStep` tail to head and updates hook documentation. | Patch received; not merged. | Assigned, decision pending. | Hold merge/build for review. | Not performed. |
| 1.14 Soul Sand / friction sample / Elytra start | Accepted source snapshot `4a0c35f`; blind review `960ce564`. | Implementation underway in separate owner task. | Source snapshot accepted; exact-code review pending. | Pending. | Not performed. |
| Passenger yaw | Implementation underway in separate owner task. | Code pending. | Pending. | Pending. | Not performed. |

The baseline contains yesterday's integrated fixes and its successful test-disabled build. Build status for any new integrated code remains pending. When a patch is accepted, record its exact code and review commits, integrate it, then use the exclusive compile slot with every Gradle Test task explicitly disabled and `-x test`. No tests, client/TAS, Gym/server, Docker, or runtime simulations are authorized. This queue does not close any source pair or claim runtime parity. Cross-version switching remains outside the correctness guarantee.

## Integration review update — 2026-10-08

Independent exact-patch review is recorded at review commit `fddb263`.

- TICK-01 is accepted and integrated as `3699009`; test-disabled compile/build remains pending.
- Passenger yaw and 1.13 pose fit are held for the requested corrections in `integration-review-2026-10-08.md`.
- Elytra comparator commit `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` was verified, but the full patch is held because the newer callsite/helper gates remain. The correction and original code are preserved in branch history and reverted from the active diff.
- No build runs until accepted review scope is recorded. Runtime validation remains unperformed.

## Build result — 2026-10-08

- TICK-01-only batch: `BUILD SUCCESSFUL` with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; every Gradle `Test` task type was disabled and no tests ran.
- Packaged JAR SHA-256: `1C3198B1E05AC5FD4CE4D84683060576CA34ED636CA81E88728FF9F6A07BCBD1`.
- Runtime parity remains unverified. Passenger yaw, pose fit, and Elytra remain held for correction and renewed review.
## Passenger yaw integration update — 2026-10-08

- Independent review 7c40951938a925fbb6fae541a95b462006113c09 ACCEPTED corrected code tip 32c8b0a7e089e32d2b755a513241ac0e58d1d562; correction 5c2cc08f6b8eaccb02f89a80de4823a33ac37503 tracks a successful direct player remount to the captured boat.
- Integrated net diff against main base ea812dc9171fdf41e02b3a2292db7d40b105c9c4: 7 files, 193 insertions. TICK-01 remains integrated.
- Build succeeded with every Gradle Test task type disabled and -x test; 18 tasks, 5 executed and 13 up-to-date. No tests or runtime checks ran.
- JAR SHA-256: 0EFADAB8C4FC87ABE8137C3E1F3E6A57CBAC15BB470E1048B739422839A7CB93.
- Pose fit and Elytra remain held for correction and renewed review.
## Ascending sneak edge integration — 2026-10-08

- F-002 accepted source snapshot `1d5f18176eccc5103c08bd1807b2f2c32f2b3376`; finding SHA-256 `4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9`; blind acceptance `0648b843fecf2358165a7c387cf348b02c66396b`.
- Exact code `14a6e23432ab6a885b5c5ce265cf2404283756f6`; independent exact-code review `c24391cc31a774da64b5d3e6d319a56a4a01e9a3` ACCEPTED. Integrated and built; see `integration-review-2026-10-08.md` for build and JAR evidence.
- Runtime validation not performed. The corrected Elytra start patch is accepted and queued as the next serial integration batch; this build contains only the ascending edge patch. Pose resize, swimming pitch, and WORLD03 remain pending.
## Full Elytra start integration — 2026-10-08

- Code commit `361c15c687a16bbbaaef2525d27a4d2277b33c3a`, reviewed code tip `bb8484823980d8f44c33c712b964275917cc93bb`; independent review tip `b8dfbc3e4d092141861759cab36272ea223a4d17` ACCEPTED.
- Integrated and built against local main `b0e7300a46aeb3fe4087bfafbcf1bcc49c76542a`; see `integration-review-2026-10-08.md` for identities, the corrected review-report digest transcription, and JAR hash. The immutable finding and blind ACCEPT identifiers agree.
- Swimming pitch is the next accepted serial batch. Pose resize and WORLD03 remain pending; no runtime validation was performed.
## Swimming pitch integration — 2026-10-08

- Code `f24fdcd2dc82906f4e111cf804de6bdb199f7f54`; independent review tip `90eaed02a299761f925f54d43a36759f26cdb650` ACCEPTED. Source snapshot and report hashes are recorded in `integration-review-2026-10-08.md`.
- Integrated after the Elytra batch and built with all Gradle `Test` tasks disabled; JAR SHA-256 `C717F4F74B51D5F41A7D6B1A1034683377C6744478B41AC863E30DF9602ADA08`.
- Pose resize and WORLD03 remain pending. No runtime validation was performed.
## Fence arm beside End Portal Frame integration — 2026-10-08

- Code `51ecf89cfdb2d077a34a56e7e1c05a9d1236f46d`; handoff `44882452607d58457d44e1aa4940f15dca9e72c8`; independent review `8838f92d46d064a7da314f4d0ca02be9ec3148bc` ACCEPTED.
- Source snapshot and provenance review identities, build command, and JAR SHA-256 are recorded in `integration-review-2026-10-08.md`.
- Seven legacy fence IDs only; pane registrations are unchanged and disjoint. Runtime validation was not performed. Pose, F-005, WORLD03, and swimming-entry remain pending review.
## F002 pose-fit float correction integration — 2026-10-08

- Corrected code `ea62550bbecc8233917fb293dbe3ee627529f621`; independent review `88792a06e72fb88f8841d8e413cdb6da026248b3` ACCEPTED. Source snapshot and report hashes plus build/JAR evidence are recorded in `integration-review-2026-10-08.md`.
- Integrated after the fence batch and built with all Gradle `Test` tasks disabled; JAR SHA-256 `59B6910B612092D3CE6566EBEBC2C5B4217BF1375EDDF471CD10210ED24F9805`.
- F-005 is next in the authorized serial queue. WORLD03 and swimming-entry remain pending review; runtime validation was not performed.
## F005 fall-flying saved-state integration — 2026-10-08

- Code `b215d723c22cf2c73601b7807056ba0d23fdbf23`; accepted tip `7d80e2eacea2ff6d088a522d8f0c1abb5768fdf3`; independent review `dd6958193b9f188341fb47f96e4cca56dd5a9a89` ACCEPTED.
- Integrated after Pose and built with all Gradle `Test` tasks disabled. Source, review, profile-load boundary, and JAR SHA-256 are recorded in `integration-review-2026-10-08.md`.
- WORLD03, swimming-entry, and slipperiness remain pending review. Runtime validation was not performed.

## WORLD03 corrected ejection integration — 2026-10-08

- Source snapshot `aa66894e64733ee729bf7176e08232d73b3bc03f` and finding SHA-256 `f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d`; blind source acceptance `a2e510bdc7b284b4b4d0e7b323f6eea079abee80`.
- Corrective code `8ae770141c4e9ca1b3f5fa3a1459d94132195306`; corrected tip `ff24abc4f199f6460f79dafd2c83a9a19d0f6121`; exact independent review `c1dee45d82c041fa83e42e35a0af8aefee35557f`, ACCEPT. Review report and exact net diff are recorded in `integration-review-2026-10-08.md`.
- Integrated at `b8332d734a3d2b64030f9ed5658c8dc990348f46` against main `0bfb72a0bc08726f2ee3a984203c08b5d90cf34e`. The merge preserves fence, ejection, F-005, pose, and prior accepted registrations.
- Test-disabled build succeeded; JAR SHA-256 `E42DB354624E47055220349F196A86EDE8480037DA264242F68203B9B332BF41`. Runtime validation not performed.
- Swimming-entry is next in the authorized serial sequence; slipperiness remains pending.

## Swimming-entry and Big Dripleaf disposition — 2026-10-08

- Swimming source snapshot `78683ba65928004ce8b7b6b9371359164a68d43b`, finding SHA-256 `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32`; clean source review `6e045f3d50f9248ab329c5bd698caa935e8a9be3` ACCEPT. Code `5c419e8d950cf8e62174a4562cc352a1d952747f`, final tip `7376c93a5dbdc3313482cd9f04c5742650756b54`; exact implementation review `6ce9b54d1b9eb3bd623e1ab172d1654da3d7af47` ACCEPT.
- Integrated after WORLD03 at `8352994616ce1fb64c2939f9240f21121b2ce76b`; build passed with all Gradle Test tasks disabled. JAR SHA-256 `E6ACA9E4FF063022ABB390BB2F207E86CD3BBDB2171B0233C118D6245315C8ED`.
- Big Dripleaf snapshot SHA-256 `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee` is ACCEPTED as no-code; its reconciliation is included without adding behavior.
- Runtime validation not performed. Slipperiness remains pending.

## Bounded slipperiness sampler integration — 2026-10-08

- Source `78683ba65928004ce8b7b6b9371359164a68d43b` / blob `96ca89de5ef2b4e84e2fd591296ab620651ee7c3` / SHA-256 `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c`; corrected-identity source review `bdda49ac76ce8b6e136917349e8b59a61d2fabe4` ACCEPT.
- Code `ad653ac30465d990b34e98601b6566ab3f150bc0`, reviewed tip `ad684f454d969117ae786f4c038ae573c3170a99`; implementation review `e1866cc05f6c2b6a77caa6f30432efa8ec3fa74e` ACCEPT. Integrated at `67bd9ed485933af705950953a783d51cdef8a23d` against main `de8eaa594677588f4a70e260561cfd2e872f69d3`.
- Test-disabled build succeeded; standalone log, absolute worktree/artifact paths, and JAR SHA-256 are recorded in `workflows/fix-implementation/runs/1.15.2-slipperiness-sampler-2026-10-08.md`.
- The accepted edge-backoff proof is no-code. The new sampler is bounded to V1_15/V1_15_2; the later legacy sampler gap remains separate. Unaccepted wider-range commit `5688e68528861c91031a021f484f80a90c887f2f` was excluded. No runtime validation was performed.

## Slipperiness source-range extension through V1_19_4 — 2026-10-08

- Source snapshot `5688e68528861c91031a021f484f80a90c887f2f` / blob `17f0906b1fa7d5e6c7995835786fb9516fc5a6d7` / recomputed raw SHA-256 `dd7d64037fb206eb5f69bf73bb5b80d929889d03c7e94a9c8a9918efb8357c8e`; source-only ACCEPT `65b711d6a5145dbcc979d982de334e2a762f401c` retained. The accepted review’s one-character SHA transcription error is explicitly corrected in its appended provenance erratum; the acceptance event itself is unchanged.
- Implementation `dd725b49a343a478b26a88528b2e3c61698a752e`, reviewed tip `5acdad5baa7261251cb442b00c6890e65f583b29`; independent implementation review `df697d4c0ad1f884551313d6f6e8c58ba40d2087` ACCEPT. Merged as source-provenance commit `acf35cc330a9b1e7de75004b4331ac1c8ec4dc64` and implementation/review commit `73f763c45a47434ad954cabeb5b5049a2443972a9f`.
- The only Java delta is the V1_19_4 registration reusing the V1_15_2 direct-double sampler. Existing registration and hook behavior remain intact. Build succeeded with all Gradle `Test` tasks disabled and `-x test`; JAR SHA-256 `F4035FBB47AC5169DE7A3510CD3728C68256FC3C778834839C79C1418D674F9C`. Full run evidence, absolute artifact/log paths and hashes are in `workflows/fix-implementation/runs/1.19.4-slipperiness-sampler-2026-10-08.md`.
- Exact first-change releases inside `(1.14.4, 1.15.2]` and `(1.19.4, 1.20.1]` remain unknown; no runtime validation was done. Passenger-crouch documentation ACCEPT `0cea9296` remains queued pending its operations-provenance erratum and is excluded from this batch.

## Passenger crouch no-code reconciliation — 2026-10-08

- No-code ACCEPT `0cea9296f492f5d619bdec93b6cc118c16828d84` and proof `dee9c6fa1f4aee135c4a38d6cbd3ba314cbcf392` are integrated as documentation-only records. The verified source finding is commit `4ee5bbce85b193f972766c324c262e375fed9bd6`, blob `5037e1f8f12ac444c5f565f63111733c65ce5f6e`, raw SHA-256 `e1d51ee7f7395415dbe5d9d9692ea1f55cdd83cf0172de27c555e70c362a29d7`.
- Operations erratum `902161eb3ebde2366283100703acb1344de67ecb` and accepted-backlog ledger were integrated; coordinator adoption is recorded by `9173c1a60246b6878b2787f6054d6173afc6da54`. The original accepted source identity and review history remain preserved. No Java code changed, no build was rerun, and the previous accepted JAR/log hashes remain unchanged.
- `src/` tree object matches task start `270e8c8`; source pair remains partial, with runtime validation not performed.

## Shallow-water current cutoff integration — 2026-10-08

- Accepted source snapshot `6374ff904018e102afd8353864d3cbcbb1b99ddd`, finding SHA-256 `9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd`; blind source review `f222615162499441f959899600744813b1276614` ACCEPTED that snapshot only. Implementation `c33269b0bd44e1e8c03895e704dac3ad40f826f6`; independent static review `e023dd454513b625a0a1d9c9cad3958545803cba` ACCEPTED. Exact code/review/build and source-manifest identities are in [the integration run record](../fix-implementation/runs/1.21.11-shallow-water-current-cutoff-integration-2026-10-08.md).
- Local `main` (`61f278e`) was merged before integration. The new `lengthSqr()` shallow-water cutoff bypass remains separate from the existing `length()`/V1_15_2 minimum-current rule. First changed 26.1 patch remains unknown; 26.1.0/.1 are not claimed.
- Test-disabled build succeeded: 18 actionable tasks, no Gradle `Test` task ran. The unique retained JAR copy SHA-256 is `B0E7B58B91311C34DE30BA192CC4B03362EC1F59615800875607A7D7AF3DA1A4`; full log SHA-256 is `4f78a61dcd98d785fda83f95fc6dbf42d9b447943cddf18887033ac09d94472c`. The prior slipperiness JAR/log remain intact. Source pair and runtime validation remain partial/unverified.

## MC1204-1206-01 no-code reconciliation — 2026-10-08

- Static no-code review `9cf9c7232c8280376034ed5d4a0ba7f84f0cf483` ACCEPTED the bounded finding; proof `da579ba8ef35f91aae0bb42fd79376057a1dddae` is integrated. Its source input is exact snapshot `1985fa829eb597acbc23685935c5f6929c078f78` / SHA-256 `eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda`, accepted by blind source review `581b5bd2cb44981c4889eaf05ea739134e332b87`.
- The accepted source body is preserved separately because the current mutable finding file differs; the ACCEPT and no-code proof remain bound only to the immutable snapshot. No new code was needed and `src/` remained unchanged after the shallow-water build. The source pair remains partial, with no exact 1.20.5 cutover claim.
- Decision 1 of the same static review identifies a separate sprint-start P2 gap. That issue is being handled separately and was not changed in this batch. No second build was run for this documentation-only reconciliation.
