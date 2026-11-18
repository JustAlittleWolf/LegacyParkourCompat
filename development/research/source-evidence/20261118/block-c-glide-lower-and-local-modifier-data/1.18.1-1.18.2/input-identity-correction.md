# Bound input identity correction

**Correction status:** identity typo only; the accepted bounded source claims are unchanged.

I rehashed the exact frozen input bytes at `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1181-1182-lower-gate/report.md` with PowerShell `Get-FileHash -Algorithm SHA256` on 2026-11-18. The actual full SHA-256 is:

`48ff fbee 8c60 d803 9bcd 0d1a 90b8 a2f0 a344 5c76 8969 d765 92b7 c8a4 87ab e5af`

Unspaced: `48fffbee8c60d8039bcd0d1a90b8a2f0a3445c768969d76592b7c8a487abe5af`.

The earlier audit handoff and verdict transcribed this as `48fffb8e8c60d8039bcd0d1a90b8a2f0a3445c768969d76592b7c8a487abe5af`; that differs at the seventh hexadecimal character: the transcribed substring is `fb8e`, while the actual substring is `fbee`. The initial audit's PowerShell hash output already displayed the correct actual value. This correction supersedes only the mistyped input identity.

I compared the report scope and claims to the independently reviewed content: the unchanged 1.18.1/1.18.2 strict `getDeltaMovement().y > -0.5` pre-move `fallDistance = 1.0F` write, mutually exclusive ordinary climb path in `travel`, and separate conditional 1.18.2 `Entity.move` ray reset. The source-manifest and Java-file hash checks remain as recorded in the original verdict. I find no substantive report mismatch and do not withdraw acceptance for that bounded claim.

The original verdict was rehashed without modification: SHA-256 `dd3e31ef596288d232f5b74c5ec91a4d134f6a07882ad35bdc6cfa7b23960ed2` (`c-glide-climb-1181-1182-lower-gate-audit/verdict.md`). Its body retains the typographical input hash, so this separate correction binds that immutable verdict to the correct frozen input identity.

**Attested by:** independent source audit worker, 2026-11-18.

