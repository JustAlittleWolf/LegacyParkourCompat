# Independent source audit: Elytra look-vector and trig continuity

TERMINAL: COMPLETED

## Bound inputs and result

- Immutable comparison input: glide-trig-mid-source/comparison.md, SHA-256 50ed17704f422fe9f99c0f6f4de7758d622ecf572d7a9d727bd40dc2538220fb (the assigned expected hash).
- Source root: D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/; source files were read only. For each release, the three files named in the hash table below were SHA-256 checked against that release's exact outer *.sources.sha256 manifest or nested .decompile-provenance.json source map. The outer ready/provenance identities were also checked where present. Result: all source-manifest comparisons and identity checks passed (30 releases, 90 files).
- Audited primary interval: all exact stable snapshots from 1.14.4 through 1.20.6 available in the canonical tree (27 releases). Separate static extension: exact 1.14.1, 1.14.2, 1.14.3 Yarn snapshots (3 releases); these conclusions are distinct from the Mojmap primary interval.

## Independent verdict

I independently confirm the bounded claim in the immutable comparison: no change was found in the dry Elytra travel look-vector provider, the angle-to-vector expression, or the Mth/MathHelper sine-table indexing across the 27-release Mojmap interval. Each LivingEntity.travel dry fall-flying branch obtains the vector from getLookAngle() and supplies that default current look vector to the glide branch. The source expression keeps float radians (pitch * (float)(Math.PI / 180.0) and -yaw * (float)(Math.PI / 180.0)), evaluates cosine then sine for yaw and then cosine then sine for pitch, and constructs (sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch). The trig table remains initialized in ascending index order from (float)Math.sin(index * Math.PI * 2.0 / 65536.0); sin retains (int)(angle * 10430.378F) & 65535, and cos retains (int)(angle * 10430.378F + 16384.0F) & 65535. No cast placement, float constant, evaluation order, mask, offset, or component order change was found.

The direct-field-to-getter provider transition at 1.17 remains a source-level alias: getXRot() and getYRot() immediately return xRot and yRot. The exact getLookAngle() calls thus retain the same input values. Access modifier and local-name changes, including calculateViewVector becoming public in 1.20.5, do not change the audited expression.

For the three added Yarn snapshots, LivingEntity.travel calls getRotationVector() in the fall-flying branch; the no-argument provider delegates to the vector expression with the current pitch and yaw fields. Its radians, trig evaluation order, component order, sine table, sin scale/cast/mask, and cos offset/cast/mask match 1.14.4. I found no look/trig difference in 1.14.1/.2/.3. This extends the bounded static result to those three exact snapshots only; it does not establish a patch introduction date earlier than the tested boundary or fill any missing release between inspected snapshots.

## Applicability limits

This verdict covers only the look-vector/trig dependency used by dry Elytra travel. It does not review activation conditions, glide acceleration/gravity, effects, collision response, other travel math, or any other movement operation. It is static source evidence, not runtime parity verification. The audited releases are explicit snapshots; no unlisted version or release boundary is inferred. The 1.14.1-.3 extension is based on Yarn manifests and is reported separately from 1.14.4+. No product implementation, wiki inventory, Git history, tests, game launches, runtime checks, or source generation were used.

## Exact source-file SHA-256

Each row lists Entity.java, LivingEntity.java, and the math source (MathHelper.java for Yarn or Mth.java for Mojmap), in that order.

| Release | Mapping | Entity.java SHA-256 | LivingEntity.java SHA-256 | Math source SHA-256 |
|---|---|---|---|---|
| 1.14.1 | yarn | ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b | 331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda | 21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a |
| 1.14.2 | yarn | 7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc | e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78 | 21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a |
| 1.14.3 | yarn | 9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14 | e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b | 21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a |
| 1.14.4 | mojmap | 31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7 | 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681 | 57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576 |
| 1.15 | mojmap | 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e | 139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79 | a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4 |
| 1.15.1 | mojmap | 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e | 139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79 | a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4 |
| 1.15.2 | mojmap | 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e | 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 | a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4 |
| 1.16 | mojmap | 27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576 | b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.1 | mojmap | 27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576 | b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.2 | mojmap | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.3 | mojmap | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.4 | mojmap | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.5 | mojmap | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.17 | mojmap | 81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef | 8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed | 24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113 |
| 1.17.1 | mojmap | ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de | 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f | 24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113 |
| 1.18 | mojmap | 69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1 | 68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7 | 10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f |
| 1.18.1 | mojmap | 69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1 | 68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7 | 10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f |
| 1.18.2 | mojmap | 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a | db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782 | 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e |
| 1.19 | mojmap | 3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766 | 00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c | 710e1a1f4e262b610406c707d8fab245d518570dd88b96828ffa9aa5a6fcda65 |
| 1.19.1 | mojmap | 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6 | fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77 | 61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f |
| 1.19.2 | mojmap | 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6 | fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77 | 61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f |
| 1.19.3 | mojmap | 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32 | 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db | f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f |
| 1.19.4 | mojmap | 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b | c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165 | 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797 |
| 1.20 | mojmap | 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759 | decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f | 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797 |
| 1.20.1 | mojmap | 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759 | decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f | 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797 |
| 1.20.2 | mojmap | d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b | 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828 | 8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce |
| 1.20.3 | mojmap | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d | f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075 |
| 1.20.4 | mojmap | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 | f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d | f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075 |
| 1.20.5 | mojmap | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 | c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2 | f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d |
| 1.20.6 | mojmap | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 | c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2 | f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d |
