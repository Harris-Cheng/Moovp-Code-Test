package com.example.moovpcodetest

import com.example.moovpcodetest.model.response.PeopleResponse
import com.example.moovpcodetest.network.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.MockitoAnnotations

class ApiTest: AutoCloseKoinTest() {
    private val apiModule = module {
        single<ApiService> {
            object : ApiService {
                override suspend fun getListOfPeople(): List<PeopleResponse> {
                    return listOf(
                        PeopleResponse(
                            _id = "abcdefg",
                            name = PeopleResponse.Name(
                                "People",
                                "A"
                            ),
                            email = "people_a@email.com",
                            picture = "picture_a",
                            location = PeopleResponse.Location(
                                123.0, 321.0
                            )
                        ),
                        PeopleResponse(
                            _id = "dthgjdgh",
                            name = PeopleResponse.Name(
                                "People",
                                "B"
                            ),
                            email = "people_b@email.com",
                            picture = "picture_b",
                            location = PeopleResponse.Location(
                                999.0, 999.0
                            )
                        ),
                    )
                }
            }
        }
    }

    private val mockApiService: ApiService by inject()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        startKoin {
            modules(apiModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testApiResponse(): Unit = runBlocking {
        // mock response
        val expectedResponse = listOf(
            PeopleResponse(
                _id = "abcdefg",
                name = PeopleResponse.Name(
                    "People",
                    "A"
                ),
                email = "people_a@email.com",
                picture = "picture_a",
                location = PeopleResponse.Location(
                    123.0, 321.0
                )
            ),
            PeopleResponse(
                _id = "dthgjdgh",
                name = PeopleResponse.Name(
                    "People",
                    "B"
                ),
                email = "people_b@email.com",
                picture = "picture_b",
                location = PeopleResponse.Location(
                    999.0, 999.0
                )
            ),
        )

        val actualResponse = mockApiService.getListOfPeople()

        assertEquals(expectedResponse, actualResponse)
    }
}