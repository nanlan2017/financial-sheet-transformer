package com.nanlan.testbase

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(value = ["test"])
abstract class ServiceTest
