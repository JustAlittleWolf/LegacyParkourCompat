# Independent explosion audit r2

Audited independently from exact Mojmap sources before opening the two r2 reports. No full-pair, runtime-parity, or trajectory claim is made.

## Finding verdict

**ACCEPT** — `incoming_writers/development/research/findings/1.20.6--1.21.11/explosion-player-knockback-r2.md`, SHA-256 `FE2E2D4D066E9E77CA43A65578D6306E6CF76F88BF1D4D2F52ECBCB655DD4EDE`.

The claim is supported by the inspected packet and listener code. In 1.20.6 the packet constructor casts a supplied `Vec3` to three floats; an absent vector becomes three `0.0F` values. The listener unconditionally adds those values to the current player velocity. The 1.21.11 packet carries an `Optional<Vec3>` via the vector stream codec, whose wire methods read/write three doubles; the listener calls `Entity.addDeltaMovement` only when present. `Entity.addDeltaMovement` filters non-finite impulses, which does not weaken the finding's explicitly finite-present-impulse case.

For absent input, old `Vec3.add(double,double,double)` computes component-wise `existing + +0.0`; its constructor retains the resulting double bits. Thus an existing `-0.0` becomes `+0.0`, while an empty optional leaves it `-0.0`. For ordinary finite nonzero components, adding `+0.0` leaves the component unchanged. The precision claim is also supported: the old constructor narrows each present component to `float`, whereas the new vector codec carries `double`; a finite nonzero, non-float-exact impulse can therefore produce a different added velocity. The finding expressly stops short of claiming a subsequent trajectory consequence.

## Boundary verdict

**ACCEPT** — `explosion_boundary/boundary-check-r2.md`, SHA-256 `C112F7E189F20FE22CCE0E46726DF1092BF95FCEF3DBEADE57CB821E5835C805`.

The paired adjacent sources establish the stated boundary: 1.21.1 retains the three-float packet and unconditional listener addition; 1.21.2 changes to `Optional<Vec3>` and conditional `ifPresent` addition. Its `ServerLevel` producer uses `Optional.ofNullable(getHitPlayers().get(player))`. I also checked the later stable-source run from 1.21.3 through 1.21.11: the optional client application and optional per-player producer remain; 1.21.9's packet adds presentation fields but retains the optional vector. The report correctly labels the result as an inspected adjacent boundary and does not claim a wider exact patch introduction or complete pair coverage.

## Source identity and integrity

The assigned source identities match local bytes: 1.20.6 Mojmap provenance `EE53E67E7AB51069A0EAAE5A0CE13D49A9F972BDC13E3C31E041AFA77F4D26E8`; 1.21.1 Mojmap provenance `6F84A596EA944A1D40C79B53EAD9D2CDD2B3C726D710A977758D15B5578CE7A9`; 1.21.2 hidden full-manifest SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`; and 1.21.11 Mojmap provenance `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`. The 1.20.6 and 1.21.1 root source-ledger digests are `56AAE10684471D7ABB1C366BD5DD431AB976112A87E6CC5C687A68F1EFF06311` and `900F956E00F6FC1300BB3D689EA49DF2B1A57BCAA54617344EF456D95D47CB48`; 1.21.2's four inspected relevant source files matched entries in its hidden manifest. For every release listed in the boundary table (1.20.6, 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.11), local SHA-256 values for `ClientboundExplodePacket.java`, `ClientPacketListener.java`, `ServerLevel.java`, and `Vec3.java` matched the respective root ledger or 1.21.2 full-manifest entries. The 1.21.11 `Entity.java` used for the finding also matched its source ledger.

The r2 revisions were re-hashed before review and matched the assigned digests above. Historical r1 verdicts supplied with the assignment are not transferred as a substitute for these r2-bound verdicts.
