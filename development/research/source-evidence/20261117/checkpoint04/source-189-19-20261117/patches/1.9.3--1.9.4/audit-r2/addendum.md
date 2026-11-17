# Audit r2 addendum

## Substantive disposition

**ACCEPT** the bounded 1.9.3 → 1.9.4 source coverage represented by the three audited reports:

- `source-review.md` — SHA-256 `E529F4646A345DDCF445886AE4F990FF0F7FED37CFAE2014F2F1AA703F4A5CF5`.
- `provider-inventory/provider-callback-inventory.md` — SHA-256 `80649ED5898E6627C0CE2B05693D11E7C8D20AC4A7065D375490D13762801711`.
- `tick-inventory-supplement/coverage.md` — SHA-256 `D00CA1BA1F564D2AACBB146C9E7AFC08740CD66652C7DEBC33590EF39B01CD7F`.

The independent checks already completed support the no-movement-difference conclusion: both exact 1,819-file source inventories match their declared hashes; paired block/provider sources are identical; and the changed player-adjacent source is metadata-only. The tick supplement closes the earlier base-tick/post-super, liquid/material, movement modifier/effect, and direct player writer gaps. The remaining external-writer concern does not leave an in-scope source-difference gate open: all paired Java source changes were isolated, and none of the substantive movement owners or listed dependencies changed. The three reports are therefore accepted for this bounded source pair and scope.

No actual in-scope source coverage gate remains. The accepted pane geometry and piston-head-provider operations remain excluded by assignment. Network/protocol inputs remain outside this review. No intermediate patch boundary, runtime parity, or client/server-boundary behavior is established; those are limits on the claims, not blockers to this source coverage acceptance.

## Incidental exposure note

An earlier automated comparison included the excluded handshake handler and exposed only a version-string metadata difference. No packet/protocol logic was examined or used as movement evidence. Per the coordinator’s clarification, this incidental metadata exposure does not invalidate the substantive independent coverage checks and does not require repeating the audit. Keep the source excluded from substantive evidence.
