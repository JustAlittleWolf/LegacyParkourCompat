# Frontier C source evidence manifest

This manifest inventories source-only report chains prepared for central preservation. Digests were recomputed from the named files. Pending reviews are listed without a guessed digest. The reports remain immutable; corrections supersede only the claims they explicitly bind.

## Independently reviewed chains

### 1.14.4 → 1.15 keyboard slow input

- Source closure: `1144-1150-keyboard-slow-input-closure/AUDIT.md` — `b4fdc658d2e6a1fba24e3ec4981d9e7be31903051a7bc0159b12d769e265734a`
- Independent audit: `1144-1150-keyboard-slow-input-audit/audit.md` — `51728b7b086c50d57f7a1f8aded562cefaba8f9ad483f05410d4c9c9615ca37b`
- The audit supports the predicate/order change and qualifies it at the `aiStep` call boundary: the new predicate reads stored shift state before that call polls the current key.

### 1.14.4 → 1.15 auto-jump scan

- Original source closure: `1144-1150-auto-jump-gate-closure/closure.md` — `82b60d6b1007782c822014f6050fa426bd1e00f01885a14a9f886cdfd74973d6`
- Published Mojmap pair supplement: `1144-1150-auto-jump-gate-closure/mojmap-pair-supplement.md` — `a1edced4bf92b7526de9e3b9c383e82d59c60c4c3b3739e65aebbed2cc627c63`
- First independent audit: `1144-1150-auto-jump-audit/audit.md` — `d1564cde9da9fafb28d0f261dd4e5c86000ef39c07741a21f962feeb04ed30b5`
- Arithmetic correction: `1144-1150-auto-jump-gate-closure/normalization-arithmetic-supplement.md` — `c19bfb775f74f5e0f9272c99992f6713e5a7bee5a3b6c7b3d74a7535b5065ff2`
- The correction retracts the “same scan math” statement and distinguishes the selected double and float `fastInvSqrt` overloads. Numerical/trajectory consequences remain unproven.
- Independent arithmetic audit: `1144-1150-auto-jump-arithmetic-audit/audit.md` — `97304746de96120fdbdda0b0abaea571ef78456521914fbbbe871a113ce78eed`

### 1.21.11 → 26.1.2 `CAN_GLIDE_THROUGH`

- Original source closure: `12111-2612-glide-tag-consumer-closure/closure.md` — `a91a30510983bb500329b3b593cd52e59ce297061a8e9f8b1bf202a3d3fbbff6`
- First independent audit: `12111-2612-glide-tag-consumer-audit/audit.md` — `3aad25c323d730804ffca7786f04e2e12ce583809e997b66624b9eab3d9ff7a2`
- Narrowing correction: `12111-2612-glide-tag-consumer-closure/correction-1.md` — `fc1c1f3a59737fedcc53dedfe61b6031b4f0d4dc05ce90a193ac614833d90489`
- Correction audit: `12111-2612-glide-tag-correction-audit/audit.md` — `618716e422181619b193a66a1ecfdec8501b3501cb5a1adb7a3453de853842fa`
- The correction withdraws ordinary-air climb/jump-lift reachability and narrows the supported claim to the conditional non-fluid fall-flying path. Effective tag membership and server producer timing remain open.

### 1.19.4 → 1.20 respawn/player-info response

- Source closure: `1194-1200-respawn-sprint-cache-closure/respawn-sprint-cache-and-player-info-game-mode.md` — `d078166067cbefd9bcc3422de7406ec131a4366ac85b093d7996490d82b23f87`
- Independent audit: `1194-1200-respawn-playerinfo-audit/audit.md` — `978b57f1c9f19bc77f72321e8b23b2d16cbbae8a6ea2e0b7f7ae430ba3d91d36`
- The audit separates command-send cache state from the direct local spectator Y-velocity response and binds the 1.20 embedded source identity.

## Independent review pending

### 1.21.10 → 26.2 player readiness

- Source closure: `12110-262-player-chunk-readiness-closure/report.md` — `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1`
- Independent audit: `12110-262-player-chunk-readiness-audit/audit.md` — `42954706e86c8ca9b35b6d557436aaa1d54ae99c1f25cb029bf7e009c50618c3`
- Lower-bound supplement for 1.21.4/.5/.6/.8/.9: pending from the same source author; exact endpoint identities will be recorded there.

### 1.14.4 → 1.15 fall-flying start

- Source closure: `1144-1150-fall-flying-start-closure/report.md` — `6aea0ccf8c9b859b3535197cba201885c3b865fc2d1b1a580402c2780e86c099`
- Independent audit: pending at `1144-1150-fall-flying-start-audit/audit.md`.

### 1.14.2 → 1.14.3 epsilon ordinary-mover correction

- Correction supplement: `AD-epsilon-ordinary-mover-correction/correction.md` — `31f7da99a6f111283190af4eab4a3c28efa40a3973e4ef362ee3e188588de4e3`
- Independent audit: pending at `AD-epsilon-ordinary-mover-audit/audit.md`.
- The correction binds the earlier gate, reachability and alias reports and narrows ordinary-player request gates and finite-coordinate limits.

## Other active source closures

- 1.15.2 → 1.16.1 input provider: `1152-1161-input-provider-closure/` — report pending.
- 1.16.3 → 1.16.4 residual changed-path classification: `1163-1164-residual-changed-path-closure/` — report pending.

No pair freeze, runtime parity or product behavior is asserted by this evidence index.
