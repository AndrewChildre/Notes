**Controller:** 

    defines what the end point should be this is the starting point. 
    has a function that calls the next step which is the processor. 
    @RequestMapping("/path") = maps the base url when used at the class level
    then we use the
    @GetMapping("/v2/path") for each endpoint in the controller class, It could be some other methods as well 
    @PostMapping, @PutMapping etc..

**Processor:**
    
    The processor has the code with actually calls the backend.
    it has a function that will need to have the correct metadata passed to it,
    in order to get the response you are expecting.
    you will then map the response to a normalizer function which will return the data to the client 
    in the structure and format it is expected