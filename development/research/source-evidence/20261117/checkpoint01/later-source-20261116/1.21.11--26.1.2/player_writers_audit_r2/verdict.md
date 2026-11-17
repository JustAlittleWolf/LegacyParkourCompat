# Independent audit r2: 1.21.11 Mojmap → 26.1.2 unobfuscated player-writer residual slice

## Immutable inputs and verdicts

- Original bounded coverage revision: `build/later-source-20261116/1.21.11--26.1.2/player_writers/coverage.md`, SHA-256 `451F966B832E6A12E7264D479BFC8B8968930F392D2C0FEFC3BB9846DE5AB30B` — **REQUEST CHANGES as a standalone roster**. It omits the reachable explosion knockback route identified by the prior audit.
- Corrective supplement revision: `build/later-source-20261116/1.21.11--26.1.2/player_writers/coverage-r2.md`, SHA-256 `0E7AA40F7FB69B21F8B532F8331A927650746310DF53C140AACF61D11E73EC79` — **ACCEPT as a targeted correction** to that immutable original.
- The combined bounded record (the original revision plus this exact supplement) — **ACCEPT for the checked residual roster only**. This is not full-pair acceptance or a pair-wide no-difference verdict.
- Prior independent audit used as context, not rewritten: `coverage-audit.md`, SHA-256 `72464A78E6469843C35423FC169188D31F8480361EE726B35EB553862F0BCB38`.

## Exact source identity and independent route check

The local provenance JSON hashes match the assigned immutable identities: 1.21.11 Mojmap `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`, and 26.1.2 unobfuscated `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`. The ready records identify the exact version/mapping and report `ready`; their source-manifest SHA-256 values match the local manifest bytes (`0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555` and `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`). The four listener/Entity source files used for the paired route match their source-ledger rows and the stated hashes. All twelve files named in the original note and its four additional route-closure files also match their source-ledger hashes.

Before reading either coverage note, I independently inspected the exact paired methods. `ClientPacketListener.handleExplosion` first applies the packet-thread gate, performs sound/particle/effect work, then applies optional `playerKnockback` to `minecraft.player` via `addDeltaMovement` (1.21.11 Mojmap lines 1311–1329, optional writer line 1328; 26.1.2 unobfuscated lines 1361–1379, optional writer line 1378). This is a reachable, direct local-player velocity response and belongs in the bounded roster.

The inherited `Entity.addDeltaMovement` methods agree in order: test supplied vector finiteness, add it to current delta movement, then call `setDeltaMovement` (1.21.11 lines 3649–3653; 26.1.2 lines 3674–3678). Thus the supplement correctly reports an operation-order/gate match and no movement difference in these compared methods. This does not establish equality of the full pair or all callers/providers.

## Other audit-described claims checked

The remaining original note's described paired packet/callback behavior is supported by the cited source slices: login and respawn/reset handling; relative correction and rotation; entity-addressed motion and position/teleport updates; passenger assignment and synced pose/sleep/item-use callbacks; effect, attribute, equipment, ability, game-mode and event handling; and the stated health/food/damage exclusions. The `setValuesFromPositionPacket` threshold (`distanceToSqr > 4096.0`), interpolation branch, direct writer order, and old-position/rotation update sequence agree. The reported 26.1.2-only prediction-handler call occurs after the correction acknowledgement and movement packet send; 1.21.11 has no such call in `handleMovePlayer`. The described scale-dimension and equipment logical-server guards match. The examined Speed, Slowness and Jump Boost definitions also match the note's stated scope. These checks found no additional correction needed within the methods the report claims to have examined.

## Remaining dependencies and boundary

The supplement accurately leaves the concrete passenger attachment/riding-position providers open and leaves the inventory/menu callback and slot-to-equipped-item path partial. The original report also remains explicitly partial. No pair-wide inventory, full tick graph, source freeze, runtime parity or product implementation was audited or accepted.
