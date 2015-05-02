STEP 1 - Proxy (Message Acceptance)
```
<env:Envelope>
   <env:Header>
      <wsse:Security>
         <wsse:UsernameToken>
            <wsse:Username>USER</wsse:Username>
            <wsse:Password>PASS</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </env:Header>
   <env:Body>
      <urn:Request>A|B|C|D|E|F</urn:Request>
   </env:Body>
</env:Envelope>
```

STEP 2 - Class Mediator (Removing pipes)
ABCDEF

STEP 3 - Backend Response
PQRXYZ

STEP 4 - Class Mediator (Split incoming value)
PQR
XYZ

STEP 5 - Payload Factory
```
<env:Envelope>
   <env:Body>
      <urn:Response>
	<urn:elementA>PQR</urn:elementA>
	<urn:elementB>ZYZ</urn:elementB>
      </urn:Response>
   </env:Body>
</env:Envelope>
```

STEP 6 - Send to Queue
